package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HomeControllerTest {

	@InjectMocks
	private HomeController homeController;

	@Mock
	private Model model;

	@Test
	void home_Ok_Test(){

		// GIVEN
		// WHEN
		String result = homeController.home(model);

		// THEN
		assertEquals("home", result);
	}

	@Test
	void adminHome_Ok_Test(){

		// GIVEN
		// WHEN
		String result = homeController.adminHome(model);

		// THEN
		assertEquals("redirect:/user/list", result);
	}





}
