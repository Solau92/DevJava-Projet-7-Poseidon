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


/**
 * Controller that manage rating pages (rating/add, rating/list, rating/update)
 */
@Slf4j
@Controller
public class RatingController {

	private static String REDIRECT_RATINGLIST_URL = "redirect:/rating/list";
	private RatingServiceImpl ratingService;
	private UserServiceImpl userService;
	private String message;

	public RatingController(RatingServiceImpl ratingService, UserServiceImpl userService) {
		this.ratingService = ratingService;
		this.userService = userService;
	}

	@RequestMapping("/rating/list")
	public String home(Model model) {
		model.addAttribute("ratings", ratingService.findAll());
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("role", userService.getLoggedUser().getRole());
		model.addAttribute("message", message);
		log.info("rating/list page display");
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		log.info("rating/add page display");
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			ratingService.save(rating);
			model.addAttribute("ratings", ratingService.findAll());
			return REDIRECT_RATINGLIST_URL;
		}
		log.info("result has error");
		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		try {
			Rating rating = ratingService.findById(id);
			log.info("rating with id " + id + " found");
			model.addAttribute("rating", rating);
			return "rating/update";
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, rating not found");
			this.message = "Error : rating not found";
			return REDIRECT_RATINGLIST_URL;
		}

	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
	                           BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			rating.setId(id);
			ratingService.save(rating);
			log.info("rating with id " + id + " updated");
			model.addAttribute("ratings", ratingService.findAll());
			return REDIRECT_RATINGLIST_URL;
		}
		log.info("result has error");
		return "rating/update";
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {

		try {
			Rating rating = ratingService.findById(id);
			log.info("rating with id " + id + " found");
			ratingService.delete(rating);
			log.info("rating with id " + id + " deleted");
			model.addAttribute("ratings", ratingService.findAll());
			return REDIRECT_RATINGLIST_URL;
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, rating not found");
			this.message = "Error : rating not found";
			return REDIRECT_RATINGLIST_URL;
		}

	}
}
