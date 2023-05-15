package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BidListController {

	private BidListServiceImpl bidListService;

	public BidListController(BidListServiceImpl bidListService) {
		this.bidListService = bidListService;
	}

	@RequestMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListService.findAll());
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bidList) {
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bidList, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			bidListService.save(bidList);
			model.addAttribute("bidLists", bidListService.findAll());
			return "redirect:/bidList/list";
		}
		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		try{
			BidList bidList = bidListService.findById(id);
			model.addAttribute("bidList", bidList);

		} catch (IllegalArgumentException exception){
			// TODO : message d'erreur ??
		}
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
	                        BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "bidList/update";
		}
		bidList.setId(id);
		bidListService.save(bidList);
		model.addAttribute("bidLists", bidListService.findAll());
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list

		try {
			BidList bidList = bidListService.findById(id);
			bidListService.delete(bidList);
			model.addAttribute("bidLists", bidListService.findAll());

		} catch (IllegalArgumentException exception){
			// TODO : message d'erreur ??

		}

		return "redirect:/bidList/list";
	}

}
