package com.seabury.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CommonService {
    void sendRedirect(HttpServletRequest request, HttpServletResponse response, String endPointUrl) throws IOException;
}
