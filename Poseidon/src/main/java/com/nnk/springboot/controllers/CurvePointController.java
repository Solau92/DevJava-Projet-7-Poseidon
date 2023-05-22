package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import com.nnk.springboot.service.UserServiceImpl;
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
public class CurvePointController {

	private CurvePointServiceImpl curvePointService;

	private UserServiceImpl userService;

	private String message;

	public CurvePointController(CurvePointServiceImpl curvePointService, UserServiceImpl userService) {
		this.curvePointService = curvePointService;
		this.userService = userService;
	}

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoints", curvePointService.findAll());
		model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
		model.addAttribute("message", message);
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
			CurvePoint curvePoint = curvePointService.findById(id);
			model.addAttribute("curvePoint", curvePoint);
			return "curvePoint/update";

		} catch (IllegalArgumentException exception){
			log.error("Illegal Argument Exception, curve point not found");
			this.message = "Error : curve point not found";
			return "redirect:/curvePoint/list";		}
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
	public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {

		try {
			CurvePoint curvePoint = curvePointService.findById(id);
			curvePointService.delete(curvePoint);
			model.addAttribute("curvePoints", curvePointService.findAll());
			return "redirect:/curvePoint/list";

		} catch (IllegalArgumentException exception) {
			log.error("Illegal Argument Exception, curve point not found");
			this.message = "Error : curve point not found";
			return "redirect:/curve point/list";		}
	}
}
