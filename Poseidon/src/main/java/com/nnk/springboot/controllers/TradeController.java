package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TradeController {

    private TradeServiceImpl tradeService;

    public TradeController(TradeServiceImpl tradeService){
        this.tradeService = tradeService;

    }

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try{
            Trade trade = tradeService.findById(id);
            model.addAttribute("trade", trade);
        } catch (IllegalArgumentException exception){
            // TODO : message d'erreur ??
        }        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(!result.hasErrors()){
            trade.setId(id);
           tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        }
        return "trade/update";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        try {
            Trade trade = tradeService.findById(id);
            tradeService.delete(trade);
            model.addAttribute("trades", tradeService.findAll());
        } catch (IllegalArgumentException exception){
            // TODO : message d'erreur ??
        }        return "redirect:/trade/list";
    }
}
