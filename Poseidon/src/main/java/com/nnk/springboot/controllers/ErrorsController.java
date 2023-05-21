//package com.nnk.springboot.controllers;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class ErrorsController implements ErrorController {
//
//	private String message;
//
//	@GetMapping("/error")
//	public String handleError(HttpServletRequest request, Model model) {
//
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//		if (status != null) {
//			message = "Error " + status;
//		}
//
//		model.addAttribute("message", message);
//
//		return "403";
//	}
//}
