package com.cybernetica.bj.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Rest authentitation error response
 * @author dmitri
 *
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse servletResponse, AuthenticationException arg2)
			throws IOException, ServletException {
		servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}