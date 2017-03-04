package com.cybernetica.bj.server.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cybernetica.bj.common.dto.BaseRestResponseDTO;

@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    BaseRestResponseDTO error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring. 
        // Here we just define response body.
    	BaseRestResponseDTO dto = new BaseRestResponseDTO();
    	//Map<String, Object> attributes = getErrorAttributes(request, false);
    	switch(response.getStatus()){
    	case 401:
    		dto.addError("error.login.failed");
    	default:
    		dto.addError("error.unhandled");
    	}
    	
        return dto;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

//    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
//        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
//        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
//    }

}