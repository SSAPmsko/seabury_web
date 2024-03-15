package com.seabury.web.controller;

import com.seabury.web.entity.dose.DocumentEntity;
import com.seabury.web.entity.dose.PlantEntity;
import com.seabury.web.service.AlfrescoService;
import com.seabury.web.service.VRDoseService;
import com.seabury.web.vo.alfresco.ALF_DocInfoVO;
import com.seabury.web.vo.dose.project.ReturnParam;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.poi.util.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class DocumentController {

    @Autowired
    AlfrescoService alfrescoService;

    @Autowired
    VRDoseService vrDoseService;


    @RequestMapping(value = {"/getDocumentList"}, method = RequestMethod.POST)
    public @ResponseBody List<ALF_DocInfoVO> getDocumentList(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) throws Exception {
        List<ALF_DocInfoVO> documentList = new ArrayList<>();

        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SearchName", "");
        parameters.put("CurrentPage", 1);

        returnMap = alfrescoService.getFullSearchDoc(parameters);

        //Add vrdose project, Scenario
        for (ALF_DocInfoVO alf_doc : (List<ALF_DocInfoVO>) returnMap.get("list")) {
            if (alf_doc.getScenarioId() != null) {

                Map<String, Object> result;

                result = vrDoseService.getScenario(alf_doc.getScenarioId());

                if (result != null) {
                    alf_doc.setScenarioName(result.get("name").toString());
                }

            }
            documentList.add(alf_doc);
        }

        return documentList;
    }

    @RequestMapping(value = {"/documentDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> documentDetailProperties(@RequestParam(value = "id", required = false) String id) throws ParseException, IOException {
        ALF_DocInfoVO whereDocument = new ALF_DocInfoVO();
        whereDocument.setObjectId(id);

        Session alf_session = alfrescoService.getCmisSession();

        ALF_DocInfoVO documentItem = alfrescoService.getDocument(alf_session, id, false);

        OperationContext context = new OperationContextImpl();
        context.setRenditionFilterString("application/pdf");

        CmisObject cmisObject = alf_session.getObject(documentItem.getObjectId(), context);

        InputStream inputStream;
        if (cmisObject instanceof Document) {

            //Original
            inputStream = ((Document) cmisObject).getContentStream().getStream();
            documentItem.setFileContentsB64(Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream)));

            //Pdf Document Rendition
            if (((Document) cmisObject).getRenditions() != null) {
                for (int i = 0; i < ((Document) cmisObject).getRenditions().size(); i++) {
                    Document renditionDoc = ((Document) cmisObject).getRenditions().get(i).getRenditionDocument();
                    if (renditionDoc != null) {
                        documentItem.setRenditionContentsB64(Base64.getEncoder().encodeToString(IOUtils.toByteArray(renditionDoc.getContentStream().getStream())));
                    }
                }
            } else {
                documentItem.setRenditionContentsB64(documentItem.getFileContentsB64());
            }
        }

        Map<String, Object> result;
        //get Scenario Info
        if (documentItem.getScenarioId() != null) {

            result = vrDoseService.getScenario(documentItem.getScenarioId());

            if (result != null) {
                documentItem.setScenarioName(result.get("name").toString());
            }
        }

        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", documentItem);
        rp.setSuccess("");

        return rp;
    }

    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Object> deleteDocument(@RequestParam(value = "id", required = false) String id) throws ParseException, IOException {
        boolean flag = alfrescoService.deleteDocument(alfrescoService.getCmisSession(), id);

        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", flag);
        rp.setSuccess("");

        return rp;
    }

    @RequestMapping(value = {"/createDocument"}, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> createDocument(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) Map<String, Object> message) throws Exception {

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

        JSONObject jObject = new JSONObject(request.getParameter("result"));
        JSONObject jDocument = jObject.getJSONObject("Document");

        ALF_DocInfoVO DocInfo = this.setDocProperties(jDocument);

        // File Path Setting
        String filepath = "d:\\upload";

        List<Map<String, Object>> uploadFileList = fileUpload(multipartHttpServletRequest, filepath);

        Document doc = null;

        if (uploadFileList.size() == 1) {

            String realFilePath = uploadFileList.get(0).get("realFilePath").toString();
            String originalFilename = uploadFileList.get(0).get("originalFilename").toString();
            Path path = FileSystems.getDefault().getPath(filepath + "\\" + realFilePath);

            String[] split = realFilePath.split("\\.");

            String extension = "";
            if (split.length > 1) {
                extension = "." + split[split.length - 1];
            }

            byte[] bytes = Files.readAllBytes(path);

            // Set UUID Name
            DocInfo.setName(UUID.randomUUID() + extension);

            doc = alfrescoService.createDocument(alfrescoService.getCmisSession(), DocInfo.getName(), DocInfo, bytes);

            File file = new File(path.toString());
            file.delete();
        }

        boolean flag = false;
        if (doc != null){
            flag = true;
        }

        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
//        rp.put("result", flag);
        rp.setSuccess("");

        return rp;
    }

    private ALF_DocInfoVO setDocProperties(JSONObject jDocument) throws ParseException {
        ALF_DocInfoVO DocInfo = new ALF_DocInfoVO();
        DocInfo.setProjectId(jDocument.has("txt_ProjectId") ? jDocument.getString("txt_ProjectId") : "");
        DocInfo.setProjectName(jDocument.has("txt_ProjectName") ? jDocument.getString("txt_ProjectName") : "");
        DocInfo.setScenarioId(jDocument.has("txt_ScenarioId") ? jDocument.getString("txt_ScenarioId") : "");
        DocInfo.setScenarioName(jDocument.has("txt_ScenarioName") ? jDocument.getString("txt_ScenarioName") : "");
        return DocInfo;
    }

    private List<Map<String, Object>> fileUpload(MultipartHttpServletRequest request, String filePath) throws Exception {

        List<Map<String, Object>> uploadFileList = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> fileMap = null;
        Iterator<String> iterator = request.getFileNames();

        MultipartFile multipartFile = null;
        // ie, edge일 경우 file path까지 넘어오기 때문에 브라우저 체크 필요
        String browser = this.getBrowser(request);

        while (iterator.hasNext()) {
            String fileName = iterator.next();
            multipartFile = request.getFile(fileName);
            if (multipartFile != null && multipartFile.getSize() > 0) {
                fileMap = new HashMap<String, Object>();

                String strFileNm = multipartFile.getOriginalFilename();
                if (browser.equals("MSIE") || browser.equals("Trident")) {
                    int startIndex = Objects.requireNonNull(strFileNm).replaceAll("\\\\", "/").lastIndexOf("/");
                    strFileNm = strFileNm.substring(startIndex + 1);
                }

                UUID randomeUUID = UUID.randomUUID();
                String realFilePath = randomeUUID + strFileNm;

                fileMap.put("originalFilename", strFileNm);
                fileMap.put("fileSize", this.getFileSize(multipartFile.getSize()));

                //파일 쓰기 처리
                if (multipartFile.getSize() > 0) {
                    File fileUploadDir = new File(filePath);

                    if (!fileUploadDir.exists()) {
                        fileUploadDir.mkdirs();
                    }

                    File convFile = new File(filePath + realFilePath);
                    multipartFile.transferTo(convFile);

                    String decryptFileName = "dec_" + realFilePath;
                    String decryptPath = filePath + decryptFileName;
                    File decryptFile = new File(decryptPath);
                    if (decryptFile.exists()) {
                        // 암호화 파일 삭제
                        convFile.delete();
                        decryptFile.renameTo(new File(filePath + realFilePath));
                    }

                    fileMap.put("realFilePath", realFilePath);
                    uploadFileList.add(fileMap);
                }
            }
        }

        return uploadFileList;
    }

    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.contains("MSIE")) {
            return "MSIE";
        } else if (header.contains("Trident")) {   // IE11 문자열 깨짐 방지
            return "Trident";
        } else if (header.contains("Chrome")) {
            return "Chrome";
        } else if (header.contains("Opera")) {
            return "Opera";
        } else if (header.contains("Safari")) {
            return "Safari";
        }
        return "Firefox";
    }

    private String getFileSize(Long fileSize) {

        String[] dataType = {"Byte", "KB", "MB"};
        String returnSize;
        int typeKey = 0;
        double changeSize = 0;
        try {
            for (int x = 0; (fileSize / (double) 1024) > 0; x++, fileSize = (long) (fileSize / (double) 1024)) {
                typeKey = x;
                changeSize = fileSize;
            }
            returnSize = changeSize + " " + dataType[typeKey];
        } catch (Exception ex) {
            returnSize = "0.0 Byte";
        }
        return returnSize;
    }
}
