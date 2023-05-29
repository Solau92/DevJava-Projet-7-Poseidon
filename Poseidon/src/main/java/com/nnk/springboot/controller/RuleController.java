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

@Slf4j
@Controller
public class RuleController {

    private RuleServiceImpl ruleService;

    private UserServiceImpl userService;

    private String message;

    private static String REDIRECT_RULELIST_URL = "redirect:/rule/list";

    public RuleController(RuleServiceImpl ruleService, UserServiceImpl userService){
        this.ruleService = ruleService;
        this.userService = userService;
    }

    @RequestMapping("/rule/list")
    public String home(Model model)
    {
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
        model.addAttribute("role", userService.getLoggedUser().getRole());
        model.addAttribute("message", message);
        return "rule/list";
    }

    @GetMapping("/rule/add")
    public String addRuleForm(Rule ruled) {
        return "rule/add";
    }

    @PostMapping("/rule/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleService.save(rule);
            model.addAttribute("rules", ruleService.findAll());
            return REDIRECT_RULELIST_URL;
        }        return "rule/add";
    }

    @GetMapping("/rule/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            Rule rule = ruleService.findById(id);
            model.addAttribute("rule", rule);
            return "rule/update";
        } catch (IllegalArgumentException exception){
            log.error("Illegal Argument Exception, rule not found");
            this.message = "Error : rule not found";
            return REDIRECT_RULELIST_URL;
        }
    }

    @PostMapping("/rule/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model) {
        if(!result.hasErrors()){
            rule.setId(id);
            ruleService.save(rule);
            model.addAttribute("rules", ruleService.findAll());
            return REDIRECT_RULELIST_URL;
        }
        return "rule/update";
    }

    @GetMapping("/rule/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id, Model model) {
        try {
            Rule rule = ruleService.findById(id);
            ruleService.delete(rule);
            model.addAttribute("rules", ruleService.findAll());
            return REDIRECT_RULELIST_URL;
        } catch (IllegalArgumentException exception){
            log.error("Illegal Argument Exception, rule not found");
            this.message = "Error : rule not found";
            return REDIRECT_RULELIST_URL;           }

    }
}