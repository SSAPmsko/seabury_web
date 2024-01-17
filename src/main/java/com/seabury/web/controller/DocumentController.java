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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
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
        for(ALF_DocInfoVO alf_doc : (List<ALF_DocInfoVO>) returnMap.get("list")){
            if(alf_doc.getScenarioId()!=null) {

                Map<String, Object> result;

                result = vrDoseService.getScenario(alf_doc.getScenarioId());

                if(result != null){
                    alf_doc.setScenarioName(result.get("name").toString());
                }

            }
            documentList.add(alf_doc);
        }

        return  documentList;
    }

    @RequestMapping(value = {"/documentDetailProperties"}, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> documentDetailProperties(@RequestParam(value = "id", required = false) String id) throws ParseException, IOException {
        ALF_DocInfoVO whereDocument = new ALF_DocInfoVO();
        whereDocument.setObjectId(id);

        Session alf_session = alfrescoService.getCmisSession();

        ALF_DocInfoVO documentItem = alfrescoService.getDocument(alf_session,id, false );

        OperationContext context = new OperationContextImpl();
        context.setRenditionFilterString("application/pdf");

        CmisObject cmisObject = alf_session.getObject(documentItem.getObjectId(), context);

        InputStream inputStream;
        if (cmisObject instanceof Document){

            //Original
            inputStream = ((Document) cmisObject).getContentStream().getStream();
            documentItem.setFileContentsB64(Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream)));

            //Pdf Document Rendition
            if(((Document)cmisObject).getRenditions() != null){
                for(int i=0; i<((Document)cmisObject).getRenditions().size(); i++ ){
                    Document renditionDoc = ((Document)cmisObject).getRenditions().get(i).getRenditionDocument();
                    if(renditionDoc !=null) {
                        documentItem.setRenditionContentsB64(Base64.getEncoder().encodeToString(IOUtils.toByteArray(renditionDoc.getContentStream().getStream())));
                    }
                }
            }else{
                documentItem.setRenditionContentsB64(documentItem.getFileContentsB64());
            }
        }

        Map<String, Object> result;
        //get Scenario Info
        if(documentItem.getScenarioId() !=null){

            result = vrDoseService.getScenario(documentItem.getScenarioId());

            if(result != null){
                documentItem.setScenarioName(result.get("name").toString());
            }
        }

        // ReturnParam 작성
        ReturnParam rp = new ReturnParam();
        rp.put("result", documentItem);
        rp.setSuccess("");

        return rp;
    }
}
