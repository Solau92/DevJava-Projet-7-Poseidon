package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementation.TradeServiceImpl;
import com.nnk.springboot.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class TradeControllerTest {

	@InjectMocks
	private TradeController tradeController;

	@Mock
	private TradeServiceImpl tradeService;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	private User loggedUser = new User();

	private Trade trade1 = new Trade();

	private Trade trade2 = new Trade();

	private List<Trade> tradeList = new ArrayList<>();


	@BeforeEach
	void setUp(){

		loggedUser.setUsername("userName");
		loggedUser.setRole("USER");

		trade1.setAccount("account1");
		trade1.setType("type1");

		trade2.setAccount(null);
		trade2.setType(null);

		tradeList.add(trade1);
		tradeList.add(trade2);
	}


	@Test
	void tradeList_Ok_Test(){

		// GIVEN
		when(tradeService.findAll()).thenReturn(tradeList);
		when(userService.getLoggedUser()).thenReturn(loggedUser);

		// WHEN
		String result = tradeController.home(model);

		// THEN
		assertEquals("trade/list", result);
	}

	@Test
	void addTradeForm_Ok_Test(){

		// GIVEN
		// WHEN
		String result = tradeController.addTradeForm(trade1);

		// THEN
		assertEquals("trade/add", result);
	}

	@Test
	void validate_Ok_Test(){

		// GIVEN
		when(tradeService.save(any(Trade.class))).thenReturn(trade1);
		when(tradeService.findAll()).thenReturn(tradeList);

		// WHEN
		String result = tradeController.validate(trade1, bindingResult, model);

		// THEN
		assertEquals("redirect:/trade/list", result);
	}

	@Test
	void validate_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = tradeController.validate(trade2, bindingResult, model);

		// THEN
		assertEquals("trade/add", result);
	}

	@Test
	void showUpdateForm_Ok_Test(){

		// GIVEN
		when(tradeService.findById(anyInt())).thenReturn(trade1);

		// WHEN
		String result = tradeController.showUpdateForm(1, model);

		// THEN
		assertEquals("trade/update", result);
	}

	@Test
	void showUpdateForm_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(tradeService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		tradeController.showUpdateForm(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception"));

	}

	@Test
	void updateTrade_Ok_Test(){

		// GIVEN
		when(tradeService.save(any(Trade.class))).thenReturn(trade1);
		when(tradeService.findAll()).thenReturn(tradeList);

		// WHEN
		String result = tradeController.updateTrade(1, trade1, bindingResult, model);

		// THEN
		assertEquals("redirect:/trade/list", result);
	}

	@Test
	void updateTrade_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = tradeController.updateTrade(1, trade1, bindingResult, model);

		// THEN
		assertEquals("trade/update", result);
	}

	@Test
	void deleteTrade_Ok_Test(){

		// GIVEN
		when(tradeService.findById(anyInt())).thenReturn(trade1);
		when(tradeService.findAll()).thenReturn(tradeList);

		// WHEN
		String result = tradeController.deleteTrade(1, model);

		// THEN
		assertEquals("redirect:/trade/list", result);
	}

	@Test
	void deleteTrade_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(tradeService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		tradeController.deleteTrade(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception"));

	}
}
