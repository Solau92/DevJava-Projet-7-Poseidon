package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.implementations.TradeServiceImpl;
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
public class TradeController {

	private static String REDIRECT_TRADELIST_URL = "redirect:/trade/list";
	private TradeServiceImpl tradeService;
	private UserServiceImpl userService;
	private String message;

	public TradeController(TradeServiceImpl tradeService, UserServiceImpl userService) {
		this.tradeService = tradeService;
		this.userService = userService;
	}

	@RequestMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trades", tradeService.findAll());
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("role", userService.getLoggedUser().getRole());
		model.addAttribute("message", message);
		log.info("trade/list page display");
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(Trade trade) {
		log.info("trade/add page display");
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			tradeService.save(trade);
			log.info("trade saved");
			model.addAttribute("trades", tradeService.findAll());
			return REDIRECT_TRADELIST_URL;
		}
		log.info("result has error");
		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		try {
			Trade trade = tradeService.findById(id);
			log.info("trade with id " + id + " found");
			model.addAttribute("trade", trade);
			return "trade/update";
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, trade not found");
			this.message = "Error : trade not found";
			return REDIRECT_TRADELIST_URL;
		}
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
	                          BindingResult result, Model model) {

		if (!result.hasErrors()) {
			log.info("result has no error");
			trade.setId(id);
			tradeService.save(trade);
			log.info("trade with id " + id + " updated");
			model.addAttribute("trades", tradeService.findAll());
			return REDIRECT_TRADELIST_URL;
		}
		log.info("result has error");
		return "trade/update";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {

		try {
			Trade trade = tradeService.findById(id);
			log.info("trade with id " + id + " found");
			tradeService.delete(trade);
			log.info("trade with id " + id + " deleted");
			model.addAttribute("trades", tradeService.findAll());
			return REDIRECT_TRADELIST_URL;
		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, trade not found");
			this.message = "Error : trade not found";
			return REDIRECT_TRADELIST_URL;
		}
	}
}
