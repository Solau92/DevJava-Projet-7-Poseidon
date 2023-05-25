package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.implementations.RatingServiceImpl;
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
public class RatingController {

    private RatingServiceImpl ratingService;

    private UserServiceImpl userService;

    private String message;


    public RatingController(RatingServiceImpl ratingService, UserServiceImpl userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.findAll());
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
        model.addAttribute("message", message);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "redirect:/rating/list";
        }        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            Rating rating = ratingService.findById(10);
            model.addAttribute("rating", rating);
            return "rating/update";
        } catch (IllegalArgumentException exception){
            log.error("Illegal Argument Exception, rating not found");
            this.message = "Error : rating not found";
            return "redirect:/rating/list";		}

    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {

        if(!result.hasErrors()){
            rating.setId(id);
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "redirect:/rating/list";
        }
        return "rating/update";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        try {
            Rating rating = ratingService.findById(id);
            ratingService.delete(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "redirect:/rating/list";
        } catch (IllegalArgumentException exception){
            log.error("Illegal Argument Exception, rating not found");
            this.message = "Error : rating not found";
            return "redirect:/rating/list";		}

    }
}
