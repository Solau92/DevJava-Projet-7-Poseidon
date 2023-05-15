package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.service.BidServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BidController {

	private BidServiceImpl bidService;

	public BidController(BidServiceImpl bidService) {
		this.bidService = bidService;
	}

	@RequestMapping("/bid/list")
	public String home(Model model) {
		model.addAttribute("bids", bidService.findAll());
		return "bid/list";
	}

	@GetMapping("/bid/add")
	public String addBidForm(Bid bid) {
		return "bid/add";
	}

	@PostMapping("/bid/validate")
	public String validate(@Valid Bid bid, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			bidService.save(bid);
			model.addAttribute("bids", bidService.findAll());
			return "redirect:/bid/list";
		}
		return "bid/add";
	}

	@GetMapping("/bid/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		try{
			Bid bid = bidService.findById(id);
			model.addAttribute("bid", bid);
		} catch (IllegalArgumentException exception){
			// TODO : message d'erreur ??
		}
		return "bid/update";
	}

	@PostMapping("/bid/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
	                        BindingResult result, Model model) {

		// TO DO : si pas d'erreur plutôt (voir up)
		if (result.hasErrors()) {
			return "bid/update";
		}
		bid.setId(id);
		bidService.save(bid);
		model.addAttribute("bids", bidService.findAll());
		return "redirect:/bid/list";
	}

	@GetMapping("/bid/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		try {
			Bid bid = bidService.findById(id);
			bidService.delete(bid);
			model.addAttribute("bids", bidService.findAll());

		} catch (IllegalArgumentException exception){
			// TODO : message d'erreur ??
		}
		return "redirect:/bid/list";
	}

}
