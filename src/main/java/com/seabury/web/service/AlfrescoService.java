package com.seabury.web.service;

import com.seabury.web.vo.alfresco.*;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface AlfrescoService {

    public void setLogin(String id, String password);

    public Session getCmisSession(String connectionName, String id, String password);

    public ALF_PeopleEntry createPeople(Session session, ALF_People alf_people) throws IOException;

    public Boolean deletePeople(Session session, String id) throws IOException;

    public ALF_PeopleEntry updatePeople(Session session, ALF_People alf_people) throws IOException;

    public ALF_PeopleEntry updatePeople(Session session, ALF_People alf_people, Map<String, Object> permissions) throws IOException;

    public ALF_PeopleEntry getPeople(Session session, String id) throws IOException;

    public List<ALF_PeopleEntry> getPeopleList(Session session) throws IOException;

    public ALF_GroupEntry createGroup(Session session, String displayName) throws IOException;

    public Boolean deleteGroup(Session session, String id) throws IOException;

    public ALF_GroupEntry updateGroup(Session session, String id, String displayName) throws IOException;

    public ALF_GroupEntry getGroup(Session session, String id) throws IOException;

    public List<ALF_GroupEntry> getGroupList(Session session) throws IOException;

    public Boolean setGroupMember(Session session, String groupId, String id, MemberType memberType) throws IOException;

    public Boolean deleteGroupMember(Session session, String groupId, String id) throws IOException;

    public Boolean setSiteMembership(Session session, String id, MemberType memberType, SiteType siteType) throws IOException;

    public Boolean deleteSiteMembership(Session session, String id) throws IOException;

    public Boolean setSiteGroup(Session session, String groupId, SiteType siteType) throws IOException;

    public Boolean deleteSiteGroup(Session session, String groupId) throws IOException;

    public Boolean setPermission(Session session, Document doc, String groupId, AlfrescoServiceImpl.PermissionType permissionType);

    public Boolean deletePermission(Session session, Document doc, String groupId);

    public Folder createFolder(Session session, String folderName);

    public Folder getFolder(Session session, String folderName);

    public Document createDocument(Session session, String documentName, ALF_DocInfoVO data, byte[] bytes) throws IOException;

    public Boolean deleteDocument(Session session, String objectId);

    // 기능 확인. 작업 필요
    public List<ALF_DocInfoVO> getFavoritesDoc(Session session, String MaxItem) throws IOException, ParseException;

    public ALF_FileEntry setFavorites(Session session, String objectId) throws IOException, JSONParseException;

    public Boolean getFavorites(Session session, String objectId) throws IOException;

    public Boolean deleteFavorites(Session session, String objectId);

    public String setCheckOut(Session alf_session, String docObjectId);

    public String setCheckIn(Session session, Document doc, Map<String, ?> properties, Boolean major, String comment, ContentStream contentStream) throws IOException;

    public Boolean cancelCheckOut(Session alf_session, String docObjectId);

    //통합검색
    public Map<String, Object> getFullSearchDoc(Session session, Map<String, Object> message) throws Exception;

    //상세검색
    public Map<String, Object> getDetailSearchDoc(Session session, Map<String, Object> message) throws Exception;

    public ALF_DocInfoVO getDocument(Session session, String objectId, Boolean alfrescoDoc) throws ParseException;

    // Document 까지 작업 됨 - 다운로드 및 PDF 변환 구현 필요
    public Map<String, Object> downloadDocument(Session session, String objectId) throws IOException;

    //수정중인 문서 조회
    List<ALF_DocInfoVO> getModifyingDoc(Session session, String maxItem) throws IOException, ParseException;

    //내가 등록한 문서
    public List<ALF_DocInfoVO> getRegisteredDoc(Session session, String MaxItem, String userName) throws IOException, ParseException;

    public ContentStream setContentStream(Session session, byte[] bytes, String documentName);

    public String date2String(Date date);

    public Date Object2Date(Object obj);

    public Map<String, Object> setDocProperties(ALF_DocInfoVO data);

    public enum PermissionType {
        write,
        read,
        all
    }

    public enum MemberType {
        GROUP,
        PERSON
    }

    public enum SiteType {
        SiteCollaborator,
        SiteConsumer,
        SiteContributor,
        SiteManager
    }
}
