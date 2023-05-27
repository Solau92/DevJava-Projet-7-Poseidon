package com.nnk.springboot.controller;

import com.nnk.springboot.service.implementations.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

	private UserServiceImpl userService;

	public AccessDeniedController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping("/access-denied")
	public String getAccessDenied(Model model){

//		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("message", "You are not authorized for the requested data");
		return "access-denied";
	}

}
