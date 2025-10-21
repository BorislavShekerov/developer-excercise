package com.yostoya.shoptill.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yostoya.shoptill.domain.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        final HttpResponse responseBody = new HttpResponse(FORBIDDEN, "Access denied. Invalid permissions");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());

        final OutputStream outputStream = response.getOutputStream();
        new ObjectMapper().writeValue(outputStream, responseBody);
        outputStream.flush();
    }
}
