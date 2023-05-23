package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controllers.BidController;
import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementation.BidServiceImpl;
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
public class BidControllerTest {

	@InjectMocks
	private BidController bidController;

	@Mock
	private BidServiceImpl bidService;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	private User loggedUser = new User();

	private Bid bid1 = new Bid();

	private Bid bid2 = new Bid();

	private List<Bid> bidList = new ArrayList<>();


	@BeforeEach
	void setUp(){

		loggedUser.setUsername("userName");
		loggedUser.setRole("USER");

		bid1.setAccount("account1");
		bid1.setType("type1");

		bid2.setAccount(null);
		bid2.setType(null);

		bidList.add(bid1);
		bidList.add(bid2);
	}


	@Test
	void bidList_Ok_Test(){

		// GIVEN
		when(bidService.findAll()).thenReturn(bidList);
		when(userService.getLoggedUser()).thenReturn(loggedUser);

		// WHEN
		String result = bidController.home(model);

		// THEN
		assertEquals("bid/list", result);
	}

	@Test
	void addBidForm_Ok_Test(){

		// GIVEN
		// WHEN
		String result = bidController.addBidForm(bid1);

		// THEN
		assertEquals("bid/add", result);
	}

	@Test
	void validate_Ok_Test(){

		// GIVEN
		when(bidService.save(any(Bid.class))).thenReturn(bid1);
		when(bidService.findAll()).thenReturn(bidList);

		// WHEN
		String result = bidController.validate(bid1, bindingResult, model);

		// THEN
		assertEquals("redirect:/bid/list", result);
	}

	@Test
	void validate_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = bidController.validate(bid2, bindingResult, model);

		// THEN
		assertEquals("bid/add", result);
	}

	@Test
	void showUpdateForm_Ok_Test(){

		// GIVEN
		when(bidService.findById(anyInt())).thenReturn(bid1);

		// WHEN
		String result = bidController.showUpdateForm(1, model);

		// THEN
		assertEquals("bid/update", result);
	}

	@Test
	void showUpdateForm_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(bidService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		bidController.showUpdateForm(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception"));

	}

	@Test
	void updateBid_Ok_Test(){

		// GIVEN
		when(bidService.save(any(Bid.class))).thenReturn(bid1);
		when(bidService.findAll()).thenReturn(bidList);

		// WHEN
		String result = bidController.updateBid(1, bid1, bindingResult, model);

		// THEN
		assertEquals("redirect:/bid/list", result);
	}

	@Test
	void updateBid_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = bidController.updateBid(1, bid1, bindingResult, model);

		// THEN
		assertEquals("bid/update", result);
	}

	@Test
	void deleteBid_Ok_Test(){

		// GIVEN
		when(bidService.findById(anyInt())).thenReturn(bid1);
		when(bidService.findAll()).thenReturn(bidList);

		// WHEN
		String result = bidController.deleteBid(1, model);

		// THEN
		assertEquals("redirect:/bid/list", result);
	}

	@Test
	void deleteBid_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(bidService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		bidController.deleteBid(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception"));

	}

}

