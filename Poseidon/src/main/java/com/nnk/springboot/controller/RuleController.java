package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.service.implementations.RuleServiceImpl;
import com.nnk.springboot.service.implementations.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

/**
 * Controller that manage rule pages (rule/add, rule/list, rule/update)
 */
@Slf4j
@Controller
public class RuleController {

	private static String REDIRECT_RULELIST_URL = "redirect:/rule/list";
	private RuleServiceImpl ruleService;
	private UserServiceImpl userService;
	private String message;

	public RuleController(RuleServiceImpl ruleService, UserServiceImpl userService) {
		this.ruleService = ruleService;
		this.userService = userService;
	}

	@RequestMapping("/rule/list")
	public String home(Model model) {
		model.addAttribute("rules", ruleService.findAll());
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("role", userService.getLoggedUser().getRole());
		model.addAttribute("message", message);
		log.info("rule/list page display");
		return "rule/list";
	}

	@GetMapping("/rule/add")
	public String addRuleForm(Rule ruled) {
		log.info("rule/add page display");
		return "rule/add";
	}

	@PostMapping("/rule/validate")
	public String validate(@Valid Rule rule, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			ruleService.save(rule);
			model.addAttribute("rules", ruleService.findAll());
			return REDIRECT_RULELIST_URL;
		}
		log.info("result has error");
		return "rule/add";
	}

	@GetMapping("/rule/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		try {
			Rule rule = ruleService.findById(id);
			log.info("rule with id " + id + " found");
			model.addAttribute("rule", rule);
			return "rule/update";
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, rule not found");
			this.message = "Error : rule not found";
			return REDIRECT_RULELIST_URL;
		}
	}

	@PostMapping("/rule/update/{id}")
	public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
	                         BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			rule.setId(id);
			ruleService.save(rule);
			log.info("rule with id " + id + " updated");
			model.addAttribute("rules", ruleService.findAll());
			return REDIRECT_RULELIST_URL;
		}
		log.info("result has error");
		return "rule/update";
	}

	@GetMapping("/rule/delete/{id}")
	public String deleteRule(@PathVariable("id") Integer id, Model model) {

		try {
			Rule rule = ruleService.findById(id);
			log.info("rule with id " + id + " found");
			ruleService.delete(rule);
			log.info("rule with id " + id + " deleted");
			model.addAttribute("rules", ruleService.findAll());
			return REDIRECT_RULELIST_URL;
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, rule not found");
			this.message = "Error : rule not found";
			return REDIRECT_RULELIST_URL;
		}
	}
}
