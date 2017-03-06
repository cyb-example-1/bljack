package com.cybernetica.bj.server.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cybernetica.bj.common.dto.RestResponseDTO;

@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

	private static final String PATH = "/error";

	private final ErrorAttributes errorAttributes;

	@Autowired
	public ErrorController(ErrorAttributes errorAttributes) {

		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(value = PATH)
	RestResponseDTO error(HttpServletRequest request, HttpServletResponse response) {
		// Appropriate HTTP response code (e.g. 404 or 500) is automatically set
		// by Spring.
		// Here we just define response body.
		RestResponseDTO dto = new RestResponseDTO();
		Map<String, Object> errorData = getError(request);
		
		switch (response.getStatus()) {
		case 400:
			dto.addError((String)errorData.get("message"));
			break;
		case 401:
			dto.addError("error.login.failed");
			break;
		default:
			dto.addError("error.unhandled");
		}
		
		return dto;
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	protected Map<String, Object> getError(HttpServletRequest aRequest) {
		Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
		String trace = (String) body.get("trace");
		if (trace != null) {
			String[] lines = trace.split("\n\t");
			body.put("trace", lines);
		}
		return body;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

}