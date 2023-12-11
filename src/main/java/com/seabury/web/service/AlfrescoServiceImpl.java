package com.seabury.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.seabury.web.vo.alfresco.*;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParseException;
import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParser;
import org.apache.chemistry.opencmis.commons.spi.AclService;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Site : 알프래스코 내의 루트 라이브러리 위치
 * <p>
 * objectId : 알프래스코의 모든 객체는 UUID를 가지고 있으며, 해당 UUID를 통해 모든 객체를 접근할 수 있다.
 * <p>
 * Cmis Api : Java 버전의 알프래스코 인터페이스, ReST Api 일부 기능만 사용 가능 (Alfresco Session, File, Document... 등 )
 * - 참조 : https://docs.alfresco.com/content-services/5.2/develop/reference/cmis-ref/
 * <p>
 * ReST Api : 알프래스코의 기능을 Url 접근할 수 있는 인터페이스
 * - 참조 : https://docs.alfresco.com/content-services/6.1/develop/rest-api-guide/
 */
@Service
public class AlfrescoServiceImpl implements AlfrescoService {

    private static final Logger logger = LoggerFactory.getLogger(AlfrescoServiceImpl.class);

    private static Map<String, Session> connections = new ConcurrentHashMap<String, Session>();

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private static final String SECONDARY_OBJECT_TYPE_IDS_PROP_NAME = "cmis:secondaryObjectTypeIds";

    private static final String ATOM_URL = "/public/cmis/versions/1.1/atom";

    @Autowired
    private AlfrescoPropertiesVO alfrescoVo;

    public String homeNetwork;

    private HttpRequestFactory requestFactory;
    private Session session;

    /**
     * 알프래스코 로그인 프로퍼티를 설정한다.
     *
     * @param id       아이디
     * @param password 패스워드
     */
    @Override
    public void setLogin(String id, String password) {
        alfrescoVo.setId(id);
        alfrescoVo.setPassword(password);
    }

    /**
     * 알프래스코의 새션을 가저온다.
     *
     * @param connectionName 새션 이름
     * @param id             아이디
     * @param password       패스워드
     * @return 알프래스코 세션
     */
    @Override
    public Session getCmisSession(String connectionName, String id, String password) {
        Session session = connections.get(id);
        if (session == null) {
            System.out.println(("Not connected, creating new connection to Alfresco with the connection id ("
                    + connectionName + ")"));

            // No connection to Alfresco available, create  a new one
            SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put(SessionParameter.USER, id);
            parameters.put(SessionParameter.PASSWORD, password);
            parameters.put(SessionParameter.ATOMPUB_URL, getAlfrescoAPIUrl() + getHomeNetwork() + ATOM_URL);
            parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            parameters.put(SessionParameter.COMPRESSION, "true");
            parameters.put(SessionParameter.CACHE_TTL_OBJECTS, "0"); // Caching is turned off

            // If there is only one repository exposed (e.g. Alfresco), these
            // lines will help detect it and its ID
            try {
                List<Repository> repositories = sessionFactory.getRepositories(parameters);
                Repository alfrescoRepository = null;
                if (repositories != null && repositories.size() > 0) {
                    System.out.println("Found (" + repositories.size() + ") Alfresco repositories");
                    alfrescoRepository = repositories.get(0);
                    System.out.println("Info about the first Alfresco repo [ID=" + alfrescoRepository.getId() + "][name="
                            + alfrescoRepository.getName() + "][CMIS ver supported="
                            + alfrescoRepository.getCmisVersionSupported() + "]");
                } else {
                    throw new CmisConnectionException("Could not connect to the Alfresco Server, no repository found!");
                }

                // Create a new session with the Alfresco repository
                session = alfrescoRepository.createSession();

                // Save connection for reuse
                connections.put(connectionName, session);
            } catch (Exception e) {
                throw new CmisConnectionException(e.getMessage());
            }

        } else {
            System.out.println("Already connected to Alfresco with the connection id (" + connectionName + ")");
        }
        return session;
    }

    /**
     * 알프래스코 내에 폴더를 생성한다.
     * 현재 버전에서는 사용하지 않습니다.
     *
     * @param session
     * @param folderName
     * @return 알프래스코 Cmis 폴더
     */
    @Override
    public Folder createFolder(Session session, String folderName) {
        Folder parentFolder = session.getRootFolder();

        // Make sure the user is allowed to create a folder under the root folder
        if (!parentFolder.getAllowableActions().getAllowableActions().contains(Action.CAN_CREATE_FOLDER)) {
            throw new CmisUnauthorizedException(
                    "Current user does not have permission to create a sub-folder in " + parentFolder.getPath());
        }

        // Check if folder already exist, if not create it
        Folder newFolder = (Folder) getObject(session, parentFolder, folderName);
        if (newFolder == null) {
            Map<String, Object> newFolderProps = new HashMap<String, Object>();
            newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
            newFolderProps.put(PropertyIds.NAME, folderName);
            newFolder = parentFolder.createFolder(newFolderProps);

            System.out.println("Created new folder: " + newFolder.getPath() + " [creator=" + newFolder.getCreatedBy()
                    + "][created=" + date2String(newFolder.getCreationDate().getTime()) + "]");
        } else {
            System.out.println("Folder already exist: " + newFolder.getPath());
        }

        return newFolder;
    }

    /**
     * 알프래스코 사이트 내에 라이브러리 폴더를 가져온다.
     *
     * @param session  알프래스코 세션
     * @param siteName 사이트 이름
     * @return 알프래스코 Cmis 폴더
     */
    @Override
    public Folder getFolder(Session session, String siteName) {
        return (Folder) session.getObjectByPath("/Sites/" + siteName.toLowerCase() + "/documentLibrary");
    }

    /**
     * 알프래스코 문서 삭제한다.
     *
     * @param session  알프래스코 세션
     * @param objectId 알프래스코의 오브젝트
     * @return 알프래스코 문서 삭제 여부
     */
    @Override
    public Boolean deleteDocument(Session session, String objectId) {

        ObjectId obj = session.getObject(objectId);
        if (obj instanceof Document) {
            Document doc = (Document) obj;
            // 문서의 모든 리비전 정보 삭제
            doc.deleteAllVersions();

            return true;
        }
        return false;
    }

    /**
     * 알프래스코 문서를 생성한다.
     *
     * @param session      알프래스코 세션
     * @param documentName 업로드 파일의 이름
     * @param data         업로드 파일의 속성 정보
     * @param bytes        업로드 파일의 바이너리 정보
     * @return 알프래스코의 Cmis 문서
     */
    @Override
    public Document createDocument(Session session, String documentName, ALF_DocInfoVO data, byte[] bytes) throws IOException {
        Folder folder = getFolder(session, alfrescoVo.getSite());

        Document newDocument = (Document) getObject(session, folder, documentName);
        if (newDocument == null) {

            // 알프래스코에 정의한 업로드 속성의 필드의 aspect 이름
            String aspectName = "P:" + alfrescoVo.getConnection() + ":document";

            List<Object> aspects = new ArrayList<>();
            aspects.add(aspectName);

            // Setup document metadata
            Map<String, Object> newDocumentProps = new HashMap<String, Object>();
            String typeId = "cmis:document";
            newDocumentProps.put(PropertyIds.OBJECT_TYPE_ID, typeId); // 문서의 타입
            newDocumentProps.put(PropertyIds.NAME, documentName); // 문서의 이름
            newDocumentProps.put(SECONDARY_OBJECT_TYPE_IDS_PROP_NAME, aspects); // 문서의 추가 속성

            // Create ContentStream
            ContentStream contentStream = this.setContentStream(session, bytes, documentName);

            // Check if we need versioning
            VersioningState versioningState = VersioningState.NONE;
            DocumentType docType = (DocumentType) session.getTypeDefinition(typeId);
            if (Boolean.TRUE.equals(docType.isVersionable())) {
                System.out.println("Document type " + typeId + " is versionable, setting MAJOR version state.");
                versioningState = VersioningState.MAJOR;
            }

            // Create versioned document object
            newDocument = folder.createDocument(newDocumentProps, contentStream, versioningState);

            System.out.println("Created new document: " + getDocumentPath(newDocument) + " [version="
                    + newDocument.getVersionLabel() + "][creator=" + newDocument.getCreatedBy() + "][created="
                    + date2String(newDocument.getCreationDate().getTime()) + "]");
        } else {
            System.out.println("Document already exist: " + getDocumentPath(newDocument));
        }

        return newDocument;
    }

    /**
     * 즐겨찾기된 문서 목록을 가져온다.
     *
     * @param session 알프래스코 세션
     * @param MaxItem 최대 목록 갯수
     * @return 알프래스코 문서의 목록
     */
    @Override
    public List<ALF_DocInfoVO> getFavoritesDoc(Session session, String MaxItem) throws IOException, ParseException {

        String personId = session.getSessionParameters().get(SessionParameter.USER);

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/"
                + personId + "/favorites?orderBy=createdAt&maxItems=" + MaxItem);

        // Get Request
        HttpRequest request = getRequestFactory().buildGetRequest(gUrl);
        ALF_FavoriteList dataList = request.execute().parseAs(ALF_FavoriteList.class);

        List<ALF_DocInfoVO> results = new ArrayList<>();

        for (ALF_FavoriteEntry entry : dataList.list.entries) {

            String objectId = entry.entry.targetGuid;
            if (objectId != null) {
                ALF_DocInfoVO data = getDocument(session, objectId, true);
                if (data != null && !StringUtils.isEmpty(data.getCmisDocument().getVersionLabel())) {
                    data.setCmisDocument(null);
                    results.add(data);
                }
            }
        }
        return results;
    }

    /**
     * 수정중인 문서 목록을 가져온다.
     *
     * @param session 알프래스코 세션
     * @param maxItem 최대 목록 갯수
     * @return 알프래스코 문서의 목록
     */
    @Override
    public List<ALF_DocInfoVO> getModifyingDoc(Session session, String maxItem) {
        String personId = session.getSessionParameters().get(SessionParameter.USER);

        String query = "SELECT D.*, C.* FROM " + alfrescoVo.getConnection() + ":document as D join cm:lockable as C on D.cmis:objectId = C.cmis:objectId " +
                "WHERE CONTAINS(D, 'PATH:\"/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "//*\"') " +
                "AND C.cm:lockOwner = '" + personId + "' " +
                "Order BY D.cmis:lastModificationDate DESC";

        ItemIterable<QueryResult> searchResult = session.query(query, false).getPage(Integer.parseInt(maxItem));

        List<ALF_DocInfoVO> results = new ArrayList<>();

        for (QueryResult resultRow : searchResult) {

            ALF_DocInfoVO data = setCmisProperties(session, resultRow, true);

            if (data != null && !StringUtils.isEmpty(data.getCmisDocument().getVersionLabel())) {
                data.setCmisDocument(null);
                results.add(data);
            }
        }

        return results;
    }

    /**
     * 등록된 문서 목록을 가져온다.
     *
     * @param session  알프래스코 세션
     * @param MaxItem  최대 목록 갯수
     * @param userName 사용자 이름 (엑셀 업로드 양식)
     * @return 알프래스코 문서의 목록
     */
    @Override
    public List<ALF_DocInfoVO> getRegisteredDoc(Session session, String MaxItem, String userName) throws IOException, ParseException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/search/versions/1/search");
        String bodyStr = String.format("{" +
                "\"query\":{" +
                "\"language\":\"afts\"," +
                "\"query\" :\"PATH:'/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "/cm:documentLibrary/*'" +
                " AND TYPE:\\\"cm:content\\\"" +
                " AND NOT cm:workingCopyLabel:'(Working Copy)'" +
                " AND " + alfrescoVo.getConnection() + ":Writer : '" + userName + "'\"" +
                "}," +
                "\"paging\":{\"maxItems\":\"" + MaxItem + "\"}," +
                "\"sort\": [{\"type\":\"FIELD\",\"field\":\"cm:created\",\"ascending\":\"false\"}]" +
                "}");

        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
        ALF_DocumentList docList = request.execute().parseAs(ALF_DocumentList.class);

        List<ALF_DocInfoVO> results = new ArrayList<>();
        for (ALF_DocumentEntry entry : docList.list.entries) {
            ALF_DocInfoVO data = getDocument(session, entry.entry.id, true);

            if (data != null && !StringUtils.isEmpty(data.getCmisDocument().getVersionLabel())) {
                data.setCmisDocument(null);
                results.add(data);
            }
        }

        return results;
    }

    /**
     * 문서의 즐겨찾기를 설정한다.
     *
     * @param session  알프래스코 세션
     * @param objectId 알프래스코 오브젝트 아아디
     * @return 알프래스코의 파일 정보
     */
    @Override
    public ALF_FileEntry setFavorites(Session session, String objectId) throws IOException, JSONParseException {

        String personId = session.getSessionParameters().get(SessionParameter.USER);

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/"
                + personId + "/favorites");

        String bodyStr = String.format("[{" +
                "\"target\":{" +
                "\"file\":{" +
                "\"guid\":\"%s\"" +
                "}}}]", objectId);

        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);

        String dataStr = request.execute().parseAsString();

        Object json = new JSONParser().parse(dataStr);

        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            JSONObject entryObject = (JSONObject) jsonObject.get("entry");

            if (entryObject != null) {
                JSONObject targetObject = (JSONObject) entryObject.get("target");

                if (targetObject != null) {
                    return new ObjectMapper().readValue(targetObject.toJSONString(),
                            ALF_FileEntry.class);
                }
            }
        }
        return null;
    }

    /**
     * 즐겨찾기 유무를 확인한다.
     *
     * @param session  알프래스코 새션
     * @param objectId 알프래스코 오브젝트 아이디
     * @return 즐겨찾기 유무
     */
    @Override
    public Boolean getFavorites(Session session, String objectId) throws IOException {
        String personId = session.getSessionParameters().get(SessionParameter.USER);

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/"
                + personId + "/favorites/" + objectId);

        HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

        try {
            String dataStr = request.execute().parseAsString();

            Object json = new JSONParser().parse(dataStr);

            if (json instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) json;
                JSONObject entryObject = (JSONObject) jsonObject.get("entry");

                if (entryObject != null) {
                    JSONObject targetObject = (JSONObject) entryObject.get("target");

                    if (targetObject != null) {
                        ALF_FileEntry fileEntry = new ObjectMapper().readValue(targetObject.toJSONString(),
                                ALF_FileEntry.class);

                        // 상세한 return 값이 필요 없으므로
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 문서의 즐겨찾기를 제거한다.
     *
     * @param session  알프래스코 세션
     * @param objectId 알프래스코 오브젝트 아이디
     * @return 즐겨찾기 삭제 여부
     */
    @Override
    public Boolean deleteFavorites(Session session, String objectId) {
        String personId = session.getSessionParameters().get(SessionParameter.USER);

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/"
                + personId + "/favorites/" + objectId);
        try {
            HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 문서의 체크아웃을 설정한다.
     *
     * @param session  알프래스코 세션
     * @param objectId 알프래스코 오브젝트 아이디
     * @return 체크아웃 된 알프래스코의 오브젝트 아이디
     */
    @Override
    public String setCheckOut(Session session, String objectId) {

        CmisObject obj = session.getObject(objectId);

        if (obj instanceof Document) {
            Document doc = (Document) obj;
            if (!doc.isVersionSeriesCheckedOut()) {
                ObjectId _objectId = doc.checkOut();

                return _objectId.getId();
            }
        }
        return null;
    }

    /**
     * 문서의 체크인을 설정한다.
     *
     * @param session       알프래스코 세션
     * @param doc           체크인 할 문서
     * @param properties    업데이트 할 문서의 속성 정보
     * @param major         Major(1.0), Minor(0.1)씩 리비전
     * @param comment       리비전 커멘트
     * @param contentStream 알프래스코의 업로할 될 문서의 Stream
     * @return 체크인 된 알프래스코의 오브젝트 아이디
     */
    @Override
    public String setCheckIn(Session session, Document doc, Map<String, ?> properties, Boolean major, String comment, ContentStream contentStream) {
        ObjectId _objectId = null;
        ContentStream _contentStream = null;
        if (doc.isVersionSeriesCheckedOut()) {
            _contentStream = contentStream != null ? contentStream : doc.getContentStream();
            _objectId = doc.checkIn(major, properties, _contentStream, comment);
        } else {
            ObjectId pwcObjectId = doc.checkOut();

            ObjectId pwcObject = session.getObject(pwcObjectId);

            if (pwcObject instanceof Document) {
                Document pwcDoc = (Document) pwcObject;
                _contentStream = contentStream != null ? contentStream : pwcDoc.getContentStream();
                _objectId = pwcDoc.checkIn(major, properties, _contentStream, comment);
            }
        }
        return _objectId.getId();
    }

    /**
     * 문서의 체크아웃을 취소한다.
     *
     * @param session  알프래스코 세션
     * @param objectId 알프래스코 오브젝트 아이디
     * @return 체크아웃의 취소 여부
     */
    @Override
    public Boolean cancelCheckOut(Session session, String objectId) {

        CmisObject obj = session.getObject(objectId);

        if (obj instanceof Document) {
            Document doc = (Document) obj;

            if (doc.isVersionSeriesCheckedOut()) {
                doc.cancelCheckOut();
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * 알프래스코의 속성, 업로드 속성, 문서의 내용을 검색한다.
     *
     * @param session 알프래스코 세션
     * @param message 검색을 위한 맵 데이터 (검색내용, 페이지 정보, 사용자 권한 등)
     * @return 검색된 문서의 결과를 맵으로 반환
     */
    @Override
    public Map<String, Object> getFullSearchDoc(Session session, Map<String, Object> message) throws Exception {
        String searchText = (String) message.get("SearchName");
        int skipNo = (Integer) message.get("CurrentPage");

        Map<String, Object> permissions = (Map<String, Object>) message.get("user_permissions");

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/search/versions/1/search");

        Map<String, Object> returnMap = new HashMap<>();

        String bodyStr = "";
        String maxItems = "10";
        String skipCount = String.valueOf((skipNo - 1) * 10);

        if (searchText.isEmpty()) {
            bodyStr = String.format("{" +
                    "\"query\":{" +
                    "\"language\":\"afts\"," +
                    "\"query\" :\"PATH:'/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "/cm:documentLibrary/*'" +
                    " AND TYPE:\\\"cm:content\\\"" +
                    " AND NOT cm:workingCopyLabel:'(Working Copy)'" +
                    " AND " + alfrescoVo.getConnection() + ":DocAutoNo:*" +
                    "\"" +
                    "}," +
                    "\"paging\":{\"maxItems\":\"" + maxItems + "\",\"skipCount\":\"" + skipCount + "\"}," +
                    "\"sort\": [{\"type\":\"FIELD\",\"field\":\"cm:created\",\"ascending\":\"false\"}]" +
                    "}");
        } else {
            bodyStr = String.format("{" +
                    "\"query\":{" +
                    "\"language\":\"afts\"," +
                    "\"query\" :\"PATH:'/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "/cm:documentLibrary/*'" +
                    " AND TYPE:\\\"cm:content\\\"" +
                    " AND NOT cm:workingCopyLabel:'(Working Copy)'" +
                    " AND (" + alfrescoVo.getConnection() + ":No : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":BookName : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":ChargePart : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":Department : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":DocClass : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":DocContent : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":DrawingDescription : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":EquipmentNo : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":FunctionalLocation : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":MaintenancePlant : '*" + searchText + "*'" +
                    " OR " + alfrescoVo.getConnection() + ":Writer : '*" + searchText + "*'" +
                    " OR cm:content : '*" + searchText + "*')\"" +
                    "}," +
                    "\"highlight\":{\"fields\":[{\"field\":\"cm:content\",\"prefix\":\"(\",\"postfix\":\")\"}]" +
                    "}," +
                    "\"paging\":{\"maxItems\":\"" + maxItems + "\",\"skipCount\":\"" + skipCount + "\"}," +
                    "\"sort\": [{\"type\":\"FIELD\",\"field\":\"cm:created\",\"ascending\":\"false\"}]" +
                    "}");
        }

        if (!StringUtils.isEmpty(bodyStr)) {
            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
            ALF_DocumentList docList = request.execute().parseAs(ALF_DocumentList.class);

            List<ALF_DocInfoVO> result = new ArrayList<>();
            for (ALF_DocumentEntry entry : docList.list.entries) {
                ALF_DocInfoVO data = getDocument(session, entry.entry.id, false);
                String thumbnailId = "";
                OperationContext context = new OperationContextImpl();
                context.setRenditionFilterString("cmis:thumbnail");

                // 21.11.22 Thumbnail null error
                try {
                    CmisObject cmObj = session.getObject(entry.entry.id, context);
                    List<Rendition> renditions = cmObj.getRenditions();

                    if (renditions != null && renditions.size() > 0) {
                        InputStream thumbStream = renditions.get(0).getContentStream().getStream();

                        byte[] imgBytes = IOUtils.toByteArray(thumbStream);
                        byte[] encodeBase64 = Base64.getEncoder().encode(imgBytes);
                        String base64DataString = new String(encodeBase64, "UTF-8");
                        data.setThumbnailUrl(base64DataString);
                    }
                    else{
                        File file = org.springframework.util.ResourceUtils.getFile("classpath:static/img/docmng/doclib.png");
                        FileInputStream stream = new FileInputStream(file);
                        byte byteArray[] = new byte[(int)file.length()];
                        stream.read(byteArray);
                        byte[] encodeBase64 = Base64.getEncoder().encode(byteArray);
                        String base64DataString = new String(encodeBase64, "UTF-8");
                        data.setThumbnailUrl(base64DataString);
                        stream.close();
                    }
                } catch (Exception e){
                    //data.setThumbnailUrl(base64DataString);
                }


                if (entry.entry.search.highlight != null) {
                    if (entry.entry.search.highlight.get(0).snippets != null) {
                        data.setHighLight(entry.entry.search.highlight.get(0).snippets.get(0));
                    }
                }

                result.add(data);
            }

            returnMap.put("total", docList.list.pagination.totalItems);
            returnMap.put("list", result);
        } else {
            returnMap.put("total", 0);
            returnMap.put("list", new ArrayList<>());
        }

        //return result.stream().filter(n -> n.getPermission() != null).collect(Collectors.toList());
        return returnMap;
    }

    /**
     * 문서의 상세 검색을 한다.
     *
     * @param session 알프래스코 세션
     * @param message 검색을 위한 맵 데이터 (코드성데이터, 검색내용, 페이지 정보, 사용자 권한 등)
     * @return 검색된 문서의 결과를 맵으로 반환
     * @throws Exception
     */
    @Override
    public Map<String, Object> getDetailSearchDoc(Session session, Map<String, Object> message) throws Exception {

        String txt_DocClass = (String) message.get("DocClass");
        String txt_DocContent = (String) message.get("DocContent");
        String txt_PlantSection = (String) message.get("MaintenancePlant"); // 21.11.23 유지보수 플랜트 검색 필드만 공정으로 수정
        String txt_BookName = (String) message.get("BookName"); // 23.02.20 by kms : Book Name Add
        //String txt_BookNo = (String) message.get("BookNo"); // 23.02.20 by kms : Book No. Add
        String txt_BookNumber = (String) message.get("BookNumber"); // 23.02.21 by kms : Book No. Add
        String txt_EquipmentNo = (String) message.get("EquipmentNo");
        String txt_FunctionalLocation = (String) message.get("FunctionalLocation");

        String txt_DocTypeLv1 = (String) message.get("DocTypeLv1");
        String txt_DocTypeLv2 = (String) message.get("DocTypeLv2");
        String txt_DocTypeLv3 = (String) message.get("DocTypeLv3");

        String txt_Content = (String) message.get("Content");
        int skipNo = (Integer) message.get("CurrentPage");

        Map<String, Object> permissions = (Map<String, Object>) message.get("user_permissions");

        String bodyStr = "";
        String maxItems = "5";
        String skipCount = String.valueOf((skipNo - 1) * 5);

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/search/versions/1/search");

        Map<String, Object> returnMap = new HashMap<>();

        if (txt_DocClass == "" && txt_DocContent == "" && txt_PlantSection == ""
                && txt_BookName == "" && txt_BookNumber == ""
                && txt_EquipmentNo == "" && txt_FunctionalLocation == "" && txt_DocTypeLv1 == ""
                && txt_DocTypeLv2 == "" && txt_DocTypeLv3 == "" && txt_Content == "") {
            bodyStr = String.format("{" +
                    "\"query\":{" +
                    "\"language\":\"afts\"," +
                    "\"query\" :\"PATH:'/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "/cm:documentLibrary/*'" +
                    " AND TYPE:\\\"cm:content\\\"" +
                    " AND NOT cm:workingCopyLabel:'(Working Copy)'" +
                    " AND " + alfrescoVo.getConnection() + ":DocAutoNo:*\"" +
                    "}," +
                    "\"paging\":{\"maxItems\":\"" + maxItems + "\",\"skipCount\":\"" + skipCount + "\"}," +
                    "\"sort\": [{\"type\":\"FIELD\",\"field\":\"cm:created\",\"ascending\":\"false\"}]" +
                    "}");
        } else {
            boolean firstWhere = false;
            String WhereStr = "";
            WhereStr += this.setWhereQuery("DocClass", txt_DocClass, WhereStr);
            WhereStr += this.setWhereQuery("DocContent", txt_DocContent, WhereStr);
            WhereStr += this.setWhereQuery("BookName", txt_BookName, WhereStr);
            WhereStr += this.setWhereQuery("BookNumber", txt_BookNumber, WhereStr);
            WhereStr += this.setWhereQuery("EquipmentNo", txt_EquipmentNo, WhereStr);
            WhereStr += this.setWhereQuery("FunctionalLocation", txt_FunctionalLocation, WhereStr);
            WhereStr += this.setWhereQuery("PlantSection", txt_PlantSection, WhereStr);
            WhereStr += this.setWhereQuery("DocTypeLv1", txt_DocTypeLv1, WhereStr);
            WhereStr += this.setWhereQuery("DocTypeLv2", txt_DocTypeLv2, WhereStr);
            WhereStr += this.setWhereQuery("DocTypeLv3", txt_DocTypeLv3, WhereStr);

            bodyStr = String.format("{" +
                    "\"query\":{" +
                    "\"language\":\"afts\"," +
                    "\"query\" :\"PATH:'/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "/cm:documentLibrary/*'" +
                    " AND TYPE:\\\"cm:content\\\"" +
                    " AND NOT cm:workingCopyLabel:'(Working Copy)'" +
                    " AND ( " + WhereStr +
                    (WhereStr.equals("") ? "" : " AND ") + "cm:content : '*" + txt_Content + "*')\"" +
                    "}," +
                    "\"highlight\":{\"fields\":[{\"field\":\"cm:content\",\"prefix\":\"(\",\"postfix\":\")\"}]" +
                    "}," +
                    "\"paging\":{\"maxItems\":\"" + maxItems + "\",\"skipCount\":\"" + skipCount + "\"}," +
                    "\"sort\": [{\"type\":\"FIELD\",\"field\":\"cm:created\",\"ascending\":\"false\"}]" +
                    "}");
        }

        if (!StringUtils.isEmpty(bodyStr)) {
            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
            ALF_DocumentList docList = request.execute().parseAs(ALF_DocumentList.class);

            List<ALF_DocInfoVO> result = new ArrayList<>();
            for (ALF_DocumentEntry entry : docList.list.entries) {
                ALF_DocInfoVO data = getDocument(session, entry.entry.id, false);
                String thumbnailId = "";
                OperationContext context = new OperationContextImpl();
                context.setRenditionFilterString("cmis:thumbnail");
                CmisObject cmObj = session.getObject(entry.entry.id, context);
                List<Rendition> renditions = cmObj.getRenditions();
                // 21.11.22 Thumbnail null error
                try {
                    if (renditions != null && renditions.size() > 0) {
                        InputStream thumbStream = renditions.get(0).getContentStream().getStream();

                        byte[] imgBytes = IOUtils.toByteArray(thumbStream);
                        byte[] encodeBase64 = Base64.getEncoder().encode(imgBytes);
                        String base64DataString = new String(encodeBase64, "UTF-8");
                        data.setThumbnailUrl(base64DataString);
                    }
                    else{
                        File file = org.springframework.util.ResourceUtils.getFile("classpath:static/img/alfresco/doclib.png");
                        FileInputStream stream = new FileInputStream(file);
                        byte[] byteArray = new byte[(int)file.length()];
                        stream.read(byteArray);
                        byte[] encodeBase64 = Base64.getEncoder().encode(byteArray);
                        String base64DataString = new String(encodeBase64, "UTF-8");
                        data.setThumbnailUrl(base64DataString);
                        stream.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                if (entry.entry.search.highlight != null) {
                    if (entry.entry.search.highlight.get(0).snippets != null) {
                        data.setHighLight(entry.entry.search.highlight.get(0).snippets.get(0));
                    }
                }
                result.add(data);
            }

            returnMap.put("total", docList.list.pagination.totalItems);
            returnMap.put("list", result);
        } else {
            returnMap.put("total", 0);
            returnMap.put("list", new ArrayList<>());
        }
        return returnMap;
    }
    /**
     * 알프래스코 문서를 가져온다.
     *
     * @param session     알프래스코 세션
     * @param objectId    알프래스코 오브젝트 아이디
     * @param alfrescoDoc Cmis 문서를 가져올지 여부
     * @return 알프래스코 문서 정보
     */
    @Override
    public ALF_DocInfoVO getDocument(Session session, String objectId, Boolean alfrescoDoc) throws ParseException {

        String query = "SELECT D.* FROM " + alfrescoVo.getConnection() + ":document as D " +
                "WHERE CONTAINS(D, 'PATH:\"/app:company_home/st:sites/cm:" + alfrescoVo.getSite() + "//*\"') " +
                "AND D.cmis:objectId = '" + objectId + "' " +
                "";

        ItemIterable<QueryResult> searchResult = session.query(query, false);

        ALF_DocInfoVO result = null;
        for (QueryResult resultRow : searchResult) {
            result = setCmisProperties(session, resultRow, alfrescoDoc);
        }

        return result;
    }

    /**
     * 알프래스코의 문서를 다운로드 한다.
     *
     * @param session    알프래스코 세션
     * @param objectId   알프래스코 오브젝트 아이디
     * @return 문서의 이름, 바이너리 정보들을 맵으로 반환
     */
    @Override
    public Map<String, Object> downloadDocument(Session session, String objectId) throws IOException {
        Map<String, Object> result = new HashMap<String, Object>();

        OperationContext context = new OperationContextImpl();
        context.setRenditionFilterString("application/pdf");
        CmisObject obj = session.getObject(objectId, context);

        if (obj instanceof Document) {
            Document doc = (Document) obj;

            String extension = this.getExtension(doc.getName());

            Property<?> docContent = doc.getProperty(alfrescoVo.getConnection() + ":DocContent");

            if (docContent != null) {
                String docTitle = (String) docContent.getFirstValue();

                byte[] bytes = IOUtils.toByteArray(doc.getContentStream().getStream());

                result.put("name", docTitle + "." + extension);
                result.put("content", bytes);

                return result;
            }
        }
        return null;
    }

    /**
     * Cmis 쿼리의 결과 로그를 확인하기 위한 메서드
     *
     * @param query        쿼리 스트링
     * @param searchResult 쿼리의 결과 데이터
     */
    private void logSearchResult(String query, ItemIterable<QueryResult> searchResult) {
        System.out.println("Results from query " + query);
        int i = 1;
        for (QueryResult resultRow : searchResult) {
            List<PropertyData<?>> properties = resultRow.getProperties();

            String logs = "--------------------------------------------\n" + i + " , ";
            for (PropertyData<?> data : properties) {
                String propertyName = data.getQueryName();
                logs += propertyName + " : " + resultRow.getPropertyByQueryName(propertyName).getValues() + "\r\n";
            }
            logs = logs.endsWith(",") ? logs.substring(0, logs.length() - 1) : logs;
            System.out.println(logs);

            i++;
        }
    }

    /**
     * Get a CMIS Object by name from a specified folder.
     *
     * @param session      Alfresco Session
     * @param parentFolder the parent folder where the object might exist
     * @param objectName   the name of the object that we are looking for
     * @return the Cmis Object if it existed, otherwise null
     */
    private CmisObject getObject(Session session, Folder parentFolder, String objectName) {
        CmisObject object = null;

        try {
            String path2Object = parentFolder.getPath();
            if (!path2Object.endsWith("/")) {
                path2Object += "/";
            }
            path2Object += objectName;
            object = session.getObjectByPath(path2Object);
        } catch (CmisObjectNotFoundException nfe0) {
            // Nothing to do, object does not exist
        }

        return object;
    }

    /**
     * Get the parent folder for the passed in Document object. Called the primary
     * parent folder in the Alfresco world as most documents only have one parent
     * folder.
     *
     * @param document the Document object to get the parent folder for
     * @return the parent Folder object, or null if it does not have a parent folder
     * and is un-filed
     */
    private Folder getDocumentParentFolder(Document document) {
        // Get all the parent folders (could be more than one if multi-filed)
        List<Folder> parentFolders = document.getParents();

        // Grab the first parent folder
        if (parentFolders.size() > 0) {
            if (parentFolders.size() > 1) {
                System.out.println("The " + document.getName() + " has more than one parent folder, it is multi-filed");
            }

            return parentFolders.get(0);
        } else {
            System.out.println("Document " + document.getName() + " is un-filed and does not have a parent folder");
            return null;
        }
    }

    /**
     * Get the parent folder path for passed in Document object
     *
     * @param document the document object to get the path for
     * @return the parent folder path, or "Un-filed" if the document is un-filed and
     * does not have a parent folder
     */
    private String getParentFolderPath(Document document) {
        Folder parentFolder = getDocumentParentFolder(document);
        return parentFolder == null ? "Un-filed" : parentFolder.getPath();
    }

    /**
     * Get the absolute path to the passed in Document object. Called the primary
     * folder path in the Alfresco world as most documents only have one parent
     * folder.
     *
     * @param document the Document object to get the path for
     * @return the path to the passed in Document object, or "Un-filed/{object
     * name}" if it does not have a parent folder
     */
    private String getDocumentPath(Document document) {
        String path2Doc = getParentFolderPath(document);
        if (!path2Doc.endsWith("/")) {
            path2Doc += "/";
        }
        path2Doc += document.getName();
        return path2Doc;
    }

    /**
     * Convert Date to String
     *
     * @param date Java Date
     * @return Java String
     */
    @Override
    public String date2String(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(date);
    }

    /**
     * Convert GregorianCalendar to Java Date
     *
     * @param obj GregorianCalendar
     * @return Java Date
     */
    @Override
    public Date Object2Date(Object obj) {
        if (obj.getClass() == GregorianCalendar.class) {
            GregorianCalendar gregorianCalendar = (GregorianCalendar) obj;

            return gregorianCalendar.getTime();
        }
        return null;
    }

    /**
     * 알프래스코 ReST Api Url
     *
     */
    private String getAlfrescoAPIUrl() {
        return "http://" + alfrescoVo.getUrl() + ":" + alfrescoVo.getPort() + "/alfresco/api/";
    }

    /**
     * 알프래스코의 네트워크 정보
     *
     */
    private String getHomeNetwork() {
        this.homeNetwork = "-default-";
//        if (this.homeNetwork == null) {
//            try {
//                GenericUrl gurl = new GenericUrl(getAlfrescoAPIUrl());
//
//                HttpRequest request = getRequestFactory().buildGetRequest(gurl);
//
//                ALF_NetworkList networkList = request.execute().parseAs(ALF_NetworkList.class);
//                System.out.println("Found " + networkList.list.pagination.totalItems + " networks.");
//
//                for (ALF_NetworkEntry networkEntry : networkList.list.entries) {
//                    if (networkEntry.entry.homeNetwork) {
//                        this.homeNetwork = networkEntry.entry.id;
//                    }
//                }
//
//                if (this.homeNetwork == null) {
//                    this.homeNetwork = "-default-";
//                }
//            } catch (Exception e) {
//                this.homeNetwork = getRequestFactory().toString() + ":" + e.getMessage();
//            }
//
//            System.out.println("Your home network appears to be: " + homeNetwork);
//        }
        return this.homeNetwork;
    }

    /**
     * 알프래스코의 ReST Api 요청하기 위한 메서드
     *
     */
    private HttpRequestFactory getRequestFactory() {
        if (this.requestFactory == null) {
            this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    request.setParser(new JsonObjectParser(new JacksonFactory()));
                    request.getHeaders().setBasicAuthentication(alfrescoVo.getId(), alfrescoVo.getPassword());
                }
            });
        }
        return this.requestFactory;
    }

    /**
     * 사이트의 루트 폴더를 가져온다.
     *
     * @param session 알프래스코 세션
     * @return Cmis 폴더
     */
    public Folder getDefaultFolder(Session session) {
        return getFolder(session, alfrescoVo.getSite());
    }

    /**
     * 문서의 바이너리 정보를 알프래스코의 Stream 정보르 변환
     *
     * @param session      알프래스코 세션
     * @param bytes        문서의 바이너리 정보
     * @param documentName 문서의 이름
     * @return 알프래스코의 업로할 될 문서의 Stream
     */
    @Override
    public ContentStream setContentStream(Session session, byte[] bytes, String documentName) {

        if (bytes != null && documentName != null) {
            // Setup document content
            String mimetype = this.convertMimetype(this.getExtension(documentName));

            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            return session.getObjectFactory().createContentStream(documentName, bytes.length, mimetype, input);
        }
        return null;
    }

    /**
     * 문서의 확장자에 따른 알프래스코 MimeType 을 변경
     *
     * @param extension 문서의 확장자
     * @return 알프래스코 MimeType 을 반환
     */
    public String convertMimetype(String extension) {
        switch (extension) {
            case "txt":
                return "text/plain";
            case "csv":
                return "text/csv";
            case "pdf":
                return "application/pdf";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "json":
                return "application/json";
            case "zip":
                return "application/zip";
            case "gif":
                return "image/gif";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "bmp":
                return "image/bmp";
            default:
                return "application/pdf; charset=UTF-8";
        }
    }

    /**
     * 알프래스코의 그룹을 생성한다.
     *
     * @param session     알프래스코 세션
     * @param displayName 디스플리이 될 그룹 정보
     * @return 알프래스코 그룹 정보
     */
    @Override
    public ALF_GroupEntry createGroup(Session session, String displayName) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/");

        String bodyStr = String.format("{" +
                "\"id\":\"%s\"," +
                "\"displayName\":\"%s\"" +
                "}", "basf_" + UUID.randomUUID(), displayName);

        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);

        return request.execute().parseAs(ALF_GroupEntry.class);
    }

    /**
     * 알프래스코의 그룹을 삭제한다.
     *
     * @param session 알프래스코 세션
     * @param id      그룹 아이디
     * @return 그룹의 삭제 여부
     */
    @Override
    public Boolean deleteGroup(Session session, String id) throws IOException {

        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/" + id);

        try {
            HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코의 그룹 정보를 업데이트 한다.
     *
     * @param session     알프래스코 세션
     * @param id          그룹 아이디
     * @param displayName 업데이트 할 그룹 정보
     * @return 알프래스코 그룹 정보
     */
    @Override
    public ALF_GroupEntry updateGroup(Session session, String id, String displayName) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/" + id);

        String bodyStr = String.format("{" +
                "\"displayName\":\"%s\"" +
                "}", displayName);

        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPutRequest(gUrl, content);

        return request.execute().parseAs(ALF_GroupEntry.class);
    }

    /**
     * 알프래스코의 그룹을 가져온다.
     *
     * @param session 알프래스코 세션
     * @param id      그룹 아이디
     * @return 알프래스코 그룹 정보
     */
    @Override
    public ALF_GroupEntry getGroup(Session session, String id) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/" + id);

        HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

        return request.execute().parseAs(ALF_GroupEntry.class);
    }

    /**
     * 알프래스코의 그룹 리스트를 가져온다.
     *
     * @param session 알프래스코 세션
     * @return 알프래스코 그룹 리스트
     * @throws IOException
     */
    @Override
    public List<ALF_GroupEntry> getGroupList(Session session) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/");

        HttpRequest request = getRequestFactory().buildGetRequest(gUrl);
        ALF_GroupList dataList = request.execute().parseAs(ALF_GroupList.class);

        return dataList.list.entries.stream().filter(n -> n.entry.id.contains("GROUP_basf_")).collect(Collectors.toList());
    }

    /**
     * 알프래스코의 사용자를 생성한다.
     *
     * @param session    알프래스코 세션
     * @param alf_people 생성할 알프래스코 사용자 정보
     * @return 알프래스코 사용자 정보
     */
    @Override
    public ALF_PeopleEntry createPeople(Session session, ALF_People alf_people) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/");

        String customPropertyStr = ", \"properties\":{" +
                "}";

        String bodyStr = String.format("{" +
                        "\"id\":\"%s\"," +
                        "\"password\":\"%s\"," +
                        "\"email\":\"%s\"," +
                        "\"firstName\":\"%s\"" +
                        customPropertyStr +
                        "}", alf_people.id,
                alf_people.password,
                alf_people.email,
                alf_people.firstName);
        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
        ALF_PeopleEntry data = request.execute().parseAs(ALF_PeopleEntry.class);

        // Default SiteManager Permission
        this.setSiteMembership(session, data.entry.id, MemberType.PERSON, SiteType.SiteManager);

        return data;
    }

    /**
     * 알프래스코 사용자를 삭제한다.
     *
     * @param session 알프래스코 세션
     * @param id      알프래스코 사용자 아이디
     * @return 사용자 삭제 여부
     */
    @Override
    public Boolean deletePeople(Session session, String id) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl("http://" + alfrescoVo.getUrl() + ":" + alfrescoVo.getPort() + "/alfresco/service/api/people/" + id);

            HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코 사용자 정보를 업데이트 한다.
     *
     * @param session     알프래스코 세션
     * @param alf_people  업데이트 될 알프래스코 사용자 정보
     * @param permissions 사용자 권한 정보
     * @return 업데이트 된 알프래스코 사용자 정보
     */
    @Override
    public ALF_PeopleEntry updatePeople(Session session, ALF_People alf_people, Map<String, Object> permissions) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/" + alf_people.id);

        String jsonProperties = ", \"properties\":{";

        if (permissions == null && permissions.size() > 0) {
            jsonProperties += ", \"" + alfrescoVo.getConnection() + ":Permissions\":\"" + getPermissionsToJson(permissions) + "\"}";
        } else {
            jsonProperties += ", \"" + alfrescoVo.getConnection() + ":Permissions\":\"\"}";
        }

        String bodyStr = String.format("{" +
                "\"email\":\"%s\"," +
                "\"firstName\":\"%s\"," +
                "\"enabled\":\"%s\"" +
                jsonProperties +
                "}", alf_people.email, alf_people.firstName, alf_people.enabled);

        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPutRequest(gUrl, content);

        return request.execute().parseAs(ALF_PeopleEntry.class);
    }

    /**
     * 알프래스코 사용자 정보를 업데이트 한다.
     *
     * @param session    알프래스코 세션
     * @param alf_people 업데이트 될 알프래스코 사용자 정보
     * @return 업데이트 된 알프래스코 사용자 정보
     */
    @Override
    public ALF_PeopleEntry updatePeople(Session session, ALF_People alf_people) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/" + alf_people.id);

        String jsonProperties = ", \"properties\":{" +
                "}";

        String bodyStr = String.format("{" +
                "\"email\":\"%s\"," +
                "\"firstName\":\"%s\"" +
                jsonProperties +
                "}", alf_people.email, alf_people.firstName);

        HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

        HttpRequest request = getRequestFactory().buildPutRequest(gUrl, content);

        return request.execute().parseAs(ALF_PeopleEntry.class);
    }

    /**
     * 사용자 정보를 가져온다.
     *
     * @param session 알프래스코 세션
     * @param id      사용자 아이디
     * @return 알프래스코 사용자 정보
     */
    @Override
    public ALF_PeopleEntry getPeople(Session session, String id) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people/" + id);

        HttpRequest request = getRequestFactory().buildGetRequest(gUrl);

        return request.execute().parseAs(ALF_PeopleEntry.class);
    }

    /**
     * 알프래스코 사용자 리스트를 가져온다.
     *
     * @param session 알프래스코 세션
     * @return 알프래스코 사용자 리스트를 반환
     */
    @Override
    public List<ALF_PeopleEntry> getPeopleList(Session session) throws IOException {
        GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/people?include=properties");

        HttpRequest request = getRequestFactory().buildGetRequest(gUrl);
        ALF_PeopleList dataList = request.execute().parseAs(ALF_PeopleList.class);

        // admin, Guest 는 시스템 계정
        return dataList.list.entries.stream().filter(n -> !n.entry.id.equals("admin") && !n.entry.id.equals("guest")).collect(Collectors.toList());
    }

    /**
     * 알프래스코의 그룹에 그룹/사용자를 할당한다.
     *
     * @param session    알프래스코 세션
     * @param groupId    그룹 아이디
     * @param id         그룹/사용자 아아디
     * @param memberType 그룹/사용자
     * @return 그룹 설정 여부
     */
    @Override
    public Boolean setGroupMember(Session session, String groupId, String id, MemberType memberType) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/" + groupId + "/members");

            String bodyStr = String.format("{" +
                    "\"id\":\"%s\"," +
                    "\"memberType\":\"%s\"" +
                    "}", id, memberType.name());

            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
            request.execute();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코의 그룹의 할당 정보를 삭제한다.
     *
     * @param session 알프래스코 세션
     * @param groupId 그룹 아이디
     * @param id      그룹/사용자 아이디
     * @return 그룹 설정 여부
     */
    @Override
    public Boolean deleteGroupMember(Session session, String groupId, String id) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl(getAlfrescoAPIUrl() + getHomeNetwork() + "/public/alfresco/versions/1/groups/" + groupId + "/members/" + id);

            HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);
            request.execute();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코의 사이트의 권한정보를 할당한다.
     *
     * @param session    알프래스코 세션
     * @param id         그룹/사용자 아이디
     * @param memberType 그룹/사용자
     * @param siteType   알프래스코의 권한
     * @return 알프래스코의 권한 설정 여부
     */
    @Override
    public Boolean setSiteMembership(Session session, String id, MemberType memberType, SiteType siteType) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl("http://" + alfrescoVo.getUrl() + ":" + alfrescoVo.getPort() + "/alfresco/service/api/sites/" + alfrescoVo.getSite() + "/memberships");
            String bodyStr = "";
            if (memberType == MemberType.GROUP) {
                bodyStr = String.format("{" +
                        "\"role\":\"%s\"," +
                        "\"group\":{\"fullName\":\"%s\"}" +
                        "}", siteType.name(), id);
            } else if (memberType == MemberType.PERSON) {
                bodyStr = String.format("{" +
                        "\"role\":\"%s\"," +
                        "\"person\":{\"userName\":\"%s\"}" +
                        "}", siteType.name(), id);
            }

            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코의 사이트의 할당된 권한정보를 삭제한다.
     *
     * @param session 알프래스코 세션
     * @param id      그룹/사용자 아아디
     * @return 알프래스코의 권한 삭제 여부
     */
    @Override
    public Boolean deleteSiteMembership(Session session, String id) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl("http://" + alfrescoVo.getUrl() + ":" + alfrescoVo.getPort() + "/alfresco/service/api/sites/" + alfrescoVo.getSite() + "/memberships/" + id);

            HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코 사이트의 그룹 할당 및 권한을 설정한다.
     *
     * @param session  알프래스코 세션
     * @param groupId  그룹 아이디
     * @param siteType 알프래스코의 권한
     * @return 알프래스코 사이트의 그룹 권한 여부
     */
    @Override
    public Boolean setSiteGroup(Session session, String groupId, SiteType siteType) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl("http://" + alfrescoVo.getUrl() + ":" + alfrescoVo.getPort() + "/alfresco/service/api/sites/" + alfrescoVo.getSite() + "/memberships");

            String bodyStr = String.format("{" +
                    "\"role\":\"%s\"," +
                    "\"group\":{\"fullName\":\"%s\"}" +
                    "}", siteType.name(), groupId);

            HttpContent content = new ByteArrayContent("application/json", bodyStr.getBytes());

            HttpRequest request = getRequestFactory().buildPostRequest(gUrl, content);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코 사이트의 할당된 그룹을 삭제한다.
     *
     * @param session 알프래스코 세션
     * @param groupId 그룹 아이디
     * @return 삭제 여부
     */
    @Override
    public Boolean deleteSiteGroup(Session session, String groupId) throws IOException {
        try {
            GenericUrl gUrl = new GenericUrl("http://" + alfrescoVo.getUrl() + ":" + alfrescoVo.getPort() + "/alfresco/service/api/sites/" + alfrescoVo.getSite() + "/memberships/" + groupId);

            HttpRequest request = getRequestFactory().buildDeleteRequest(gUrl);
            request.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코 문서의 그룹 권한을 설정한다.
     *
     * @param session        알프래스코 세션
     * @param doc            Cmis 문서
     * @param groupId        그룹 아이디
     * @param permissionType All / Write / Read
     * @return 권한 설정 여부
     */
    @Override
    public Boolean setPermission(Session session, Document doc, String groupId, PermissionType permissionType) {
        try {
            List<String> permissions = new ArrayList<String>();
            permissions.add("cmis:" + permissionType.name());

            Ace aceIn = session.getObjectFactory().createAce(groupId, permissions);
            List<Ace> aceListIn = new ArrayList<Ace>();
            aceListIn.add(aceIn);
            doc.addAcl(aceListIn, AclPropagation.REPOSITORYDETERMINED);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코 문서의 그룹 권한을 삭제한다.
     *
     * @param session 알프래스코 세션
     * @param doc     Cmis 문서
     * @param groupId 그룹 아이디
     * @return 권한 삭제 여부
     */
    @Override
    public Boolean deletePermission(Session session, Document doc, String groupId) {
        try {
            String getRepositoryId = session.getRepositoryInfo().getId();
            AclService aclService = session.getBinding().getAclService();

            Acl acl = aclService.getAcl(getRepositoryId, doc.getId(), false, null);
            List<Ace> aceListOut = acl.getAces().stream().filter(n -> n.getPrincipalId().equals(groupId)).collect(Collectors.toList());

            doc.removeAcl(aceListOut, AclPropagation.REPOSITORYDETERMINED);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 알프래스코의 문서 정보를 업데이트 하기위에 맵데이터로 변환
     *
     * @param data 알프래스코의 문서 정보
     * @return 알프래스코의 문서 속성 맵 데이터를 반환
     */
    @Override
    public Map<String, Object> setDocProperties(ALF_DocInfoVO data) {
        Map<String, Object> properties = new HashMap<>();

        // Alfresco cmis:Document
//            properties.put("cm:title", data.getTitle());
        if (data.getName() != null && !data.getName().equals("")) {
            properties.put("cmis:name", data.getName());
        }

        // Alfresco ife:Document
        if (data.getProjectId() != null) {
            properties.put(alfrescoVo.getConnection() + ":ProjectId", data.getProjectId());
        }
        if (data.getScenarioId() != null) {
            properties.put(alfrescoVo.getConnection() + ":ScenarioId", data.getScenarioId());
        }
        return properties;
    }

    /**
     * 문서의 확장자를 얻기위한 메서드
     *
     * @param documentName 문서의 이름 ex) Test.txt
     * @return 문서의 확장자를 반환
     */
    public String getExtension(String documentName) {
        String[] split = documentName.split("\\.");
        String extension = "";
        if (split.length > 1) {
            extension = split[split.length - 1];
        }
        return extension;
    }

    /**
     * 사용자의 권한 리스트를 Json 으로 변환한다.
     *
     * @param permissions 권한 맵 데이터
     * @return Json 스트링
     */
    public String getPermissionsToJson(Map<String, Object> permissions) {
        org.json.JSONObject jsonObject = new org.json.JSONObject(permissions);
        return jsonObject != null ? jsonObject.toString().replace("\"", "\\\"") : "";
    }

    public ALF_DocInfoVO setCmisProperties(Session session, QueryResult resultRow, Boolean alfrescoDoc){
        ALF_DocInfoVO data = new ALF_DocInfoVO();
        data.setName((String)resultRow.getPropertyByQueryName("D.cmis:name").getFirstValue());
        data.setObjectId((String)resultRow.getPropertyByQueryName("D.cmis:objectId").getFirstValue());

        // AclService - Permissions
        AclService aclService = session.getBinding().getAclService();
        String getRepositoryId = session.getRepositoryInfo().getId();

        Acl acl =  aclService.getAcl(getRepositoryId, data.getObjectId(), false,  null);
        List<Ace> aceList = acl.getAces();

        // Group Role
        List<Ace> whereList = aceList.stream().filter(n -> n.getPrincipalId().equals("GROUP_basf_" + session.getSessionParameters().get(SessionParameter.USER))).collect(Collectors.toList());

        if (whereList.size() > 0){
            // Permission
            List<String> wherePermissionList = whereList.stream().findAny().get().getPermissions().stream().filter(n -> n.contains("cmis:")).collect(Collectors.toList());
            if (wherePermissionList.size() > 0){
                data.setPermission(wherePermissionList.stream().findAny().get());
            }
        }

        // Alfresco Doc
        if (alfrescoDoc){
            CmisObject obj = session.getObject(data.getObjectId());

            if (obj instanceof Document){
                data.setCmisDocument((Document)obj);
            }
        }

        return data;
    }

    public Integer TryParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Double TryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public Date TryParseDate(SimpleDateFormat dateFormat, String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException ex) {
            return null;
        }
    }

    public String setWhereQuery(String alfrescoType, String value, String isFirst){
        if (!value.isEmpty()){
            return isFirst.isEmpty() ? alfrescoVo.getConnection() + ":" + alfrescoType + " : '*" + value.replace("-", "_") + "*'" :" AND " + alfrescoVo.getConnection() + ":" + alfrescoType + " : '*" + value.replace("-", "_") + "*'";
        }
        return "";
    }
}

