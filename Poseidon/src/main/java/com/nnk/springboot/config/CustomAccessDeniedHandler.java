package com.nnk.springboot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * Implements AccessDeniedHandler.
 * Handles an access denied failure.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * Handles an access denied failure.
	 * @param request
	 * @param response
	 * @param accessDeniedException that caused the invocation
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {

		}
		response.sendRedirect(request.getContextPath() + "/access-denied");
	}
}
