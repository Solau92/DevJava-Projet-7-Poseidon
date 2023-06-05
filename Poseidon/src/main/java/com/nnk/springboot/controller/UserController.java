package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller that manage user pages (user/add, user/list, user/update)
 */
@Slf4j
@Controller
public class UserController {

	private static String REDIRECT_USERLIST_URL = "redirect:/user/list";
	String message;
	private UserServiceImpl userService;


	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@RequestMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("message", message);
		log.info("user/list page display");
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUserForm(User bid) {
		log.info("user/add page display");
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {

		if (userService.findByUsername(user.getUsername()) != null) {
			log.info("result has error : already an account registered with the username");
			result.rejectValue("username", null, "There is already an account registered with this username");
			return "user/add";
		}

		if (!result.hasErrors()) {
			log.info("result has no error");
			userService.save(user);
			model.addAttribute("users", userService.findAll());
			return REDIRECT_USERLIST_URL;
		}
		log.info("result has error");
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		try {
			User user = userService.findById(id);
			log.info("user with id " + id + " found");
			model.addAttribute("user", user);
			return "user/update";
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, user not found");
			this.message = "Error : user not found";
			return REDIRECT_USERLIST_URL;
		}
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user,
	                         BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			user.setId(id);
			userService.save(user);
			log.info("user with id " + id + " updated");
			model.addAttribute("users", userService.findAll());
			return REDIRECT_USERLIST_URL;
		}
		log.info("result has error");
		return "user/update";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {

		try {
			User user = userService.findById(id);
			log.info("user with id " + id + " found");
			userService.delete(user);
			log.info("user with id " + id + " deleted");
			model.addAttribute("users", userService.findAll());
			return REDIRECT_USERLIST_URL;
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, user not found");
			this.message = "Error : user not found";
			return REDIRECT_USERLIST_URL;
		}

	}
}
