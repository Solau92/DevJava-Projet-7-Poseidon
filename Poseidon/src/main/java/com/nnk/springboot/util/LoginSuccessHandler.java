//package com.nnk.springboot.util;
//
//import com.nnk.springboot.configtests.CustomUserDetailsService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//	                                    Authentication authentication) throws ServletException, IOException {
//
//		CustomUserDetailsService userDetails = (CustomUserDetailsService) authentication.getPrincipal();
//
//		String redirectURL = request.getContextPath();
//
//		if (userDetails.hasRole("ADMIN")) {
//			redirectURL = "/user/list";
//		} else if (userDetails.hasRole("USER")) {
//			redirectURL = "/bid/list";
//		}
//
//		response.sendRedirect(redirectURL);
//
//	}
//}
