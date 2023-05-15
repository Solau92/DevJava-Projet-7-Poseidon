package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class CurvePointController {

	private CurvePointServiceImpl curvePointService;

	public CurvePointController(CurvePointServiceImpl curvePointService) {
		this.curvePointService = curvePointService;
	}

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoints", curvePointService.findAll());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint bid) {
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

		if(!result.hasErrors()) {
			curvePointService.save(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());
			return "redirect:/curvePoint/list";
		}
		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

		try {
			CurvePoint curvePoint = curvePointService.finById(id);
			model.addAttribute("curvePoint", curvePoint);

		} catch (IllegalArgumentException exception){
			// TODO : message d'erreur ??
		}

		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
	                        BindingResult result, Model model) {

		if(!result.hasErrors()){
			curvePoint.setId(id);
			curvePointService.save(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());
			return "redirect:/curvePoint/list";
		}
		return "curvePoint/update";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {

		try {
			CurvePoint curvePoint = curvePointService.finById(id);
			curvePointService.delete(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());

		} catch (IllegalArgumentException exception) {
			// TODO : message d'erreur ??
		}
		return "redirect:/curvePoint/list";
	}
}
