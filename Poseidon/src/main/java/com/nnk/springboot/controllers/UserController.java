package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class UserController {

    private UserServiceImpl userService;

    String message;

    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("message", message);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUserForm(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        try{
            User user = userService.findById(id);
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        } catch (IllegalArgumentException exception){
            log.error("Illegal Argument Exception, user not found");
            this.message = "Error : user not found";
            return "redirect:/user/list";
        }

    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            user.setId(id);
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/update";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        try {
            User user = userService.findById(id);
            userService.delete(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        } catch (IllegalArgumentException exception){
            log.error("Illegal Argument Exception, user not found");
            this.message = "Error : user not found";
            return "redirect:/user/list";
        }

    }
}
