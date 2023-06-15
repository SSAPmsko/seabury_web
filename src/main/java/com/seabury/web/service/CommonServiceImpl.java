package com.seabury.web.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CommonServiceImpl  implements CommonService{
    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String endPointUrl) throws IOException {
        if(endPointUrl!= null && endPointUrl.indexOf("://") < 0){
            String urlPrefix = request.getRequestURL().toString();

            urlPrefix = urlPrefix.substring(0,urlPrefix.indexOf(request.getContextPath())+request.getContextPath().length());

            if(urlPrefix.startsWith("http://") && !urlPrefix.startsWith("http://localhost")){
                urlPrefix = urlPrefix.replaceAll("http://","https://");
            }
            System.out.println("urlPrefix:"+urlPrefix);

            endPointUrl = urlPrefix + endPointUrl;
        }
        response.sendRedirect(endPointUrl);
    }
}
