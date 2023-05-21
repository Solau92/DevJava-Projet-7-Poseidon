package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
import com.nnk.springboot.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;


@Controller
public class RatingController {

    private RatingServiceImpl ratingService;

    private UserServiceImpl userService;

    public RatingController(RatingServiceImpl ratingService, UserServiceImpl userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.findAll());
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
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
            Rating rating = ratingService.findById(id);
            model.addAttribute("rating", rating);
        } catch (IllegalArgumentException exception){
            // TODO : message d'erreur ??
        }
        return "rating/update";
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
        } catch (IllegalArgumentException exception){
            // TODO : message d'erreur ??
        }
        return "redirect:/rating/list";
    }
}
