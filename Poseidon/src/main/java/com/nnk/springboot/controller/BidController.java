package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.service.implementations.BidServiceImpl;
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

@Slf4j
@Controller
public class BidController {

	private BidServiceImpl bidService;

	private UserServiceImpl userService;

	private String message;

	private static String REDIRECT_BIDLIST_URL = "redirect:/bid/list";

	public BidController(BidServiceImpl bidService, UserServiceImpl userService) {
		this.bidService = bidService;
		this.userService = userService;
	}

	@RequestMapping("/bid/list")
	public String home(Model model) {
		model.addAttribute("bids", bidService.findAll());
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("role", userService.getLoggedUser().getRole());
		model.addAttribute("message", message);
		log.info("bid/list page display");
		return "bid/list";
	}

	@GetMapping("/bid/add")
	public String addBidForm(Bid bid) {
		log.info("bid/add page display");
		return "bid/add";
	}

	@PostMapping("/bid/validate")
	public String validate(@Valid Bid bid, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			bidService.save(bid);
			log.info("bid saved");
			model.addAttribute("bids", bidService.findAll());
			return REDIRECT_BIDLIST_URL;
		}
		log.info("result has error");
		return "bid/add";
	}

	@GetMapping("/bid/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		try{
			Bid bid = bidService.findById(id);
			log.info("bid with id " + id + " found");
			model.addAttribute("bid", bid);
			return "bid/update";
		} catch (IllegalArgumentException exception){
			log.error("Illegal Argument Exception, bid not found");
			this.message = "Error : bid not found";
			return REDIRECT_BIDLIST_URL;
		}
	}

	@PostMapping("/bid/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
	                        BindingResult result, Model model) {

		if(!result.hasErrors()) {
			log.info("result has no error");
			bid.setId(id);
			bidService.save(bid);
			log.info("bid with id " + id + " updated");
			model.addAttribute("bids", bidService.findAll());
			return REDIRECT_BIDLIST_URL;
		}
		log.info("result has error");
		return "bid/update";
	}

	@GetMapping("/bid/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		try {
			Bid bid = bidService.findById(id);
			log.info("bid with id " + id + " found");
			bidService.delete(bid);
			log.info("bid with id " + id + " deleted");
			model.addAttribute("bids", bidService.findAll());
			return REDIRECT_BIDLIST_URL;
		} catch (IllegalArgumentException exception){
			log.error("Illegal Argument Exception, bid not found");
			this.message = "Error : bid not found";
			return REDIRECT_BIDLIST_URL;
		}
	}

}
