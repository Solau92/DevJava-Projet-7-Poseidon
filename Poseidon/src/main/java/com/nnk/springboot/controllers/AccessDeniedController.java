package com.nnk.springboot.controllers;

import com.nnk.springboot.service.UserServiceImpl;
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

		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("message", "Forbidden");
		return "access-denied";
	}

}
