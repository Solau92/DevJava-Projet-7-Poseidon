package com.nnk.springboot.controller;

import com.nnk.springboot.service.implementations.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AccessDeniedController {

	private UserServiceImpl userService;

	public AccessDeniedController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping("/access-denied")
	public String getAccessDenied(Model model){
		model.addAttribute("message", "You are not authorized for the requested data");
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		log.info("access-denied page");
		return "access-denied";
	}

}
