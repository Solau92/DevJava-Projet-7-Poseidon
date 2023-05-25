package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementations.RatingServiceImpl;
import com.nnk.springboot.service.implementations.UserServiceImpl;
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
public class RatingControllerTest {

	@InjectMocks
	private RatingController ratingController;

	@Mock
	private RatingServiceImpl ratingService;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	private User loggedUser = new User();

	private Rating rating1 = new Rating();

	private Rating rating2 = new Rating();

	private List<Rating> ratingList = new ArrayList<>();


	@BeforeEach
	void setUp(){

		loggedUser.setUsername("userName");
		loggedUser.setRole("USER");

		rating1.setId(1);
		rating1.setMoodysRating("mr1");

		rating2.setId(2);
		rating2.setMoodysRating("mr2");

		ratingList.add(rating1);
		ratingList.add(rating2);
	}

	@Test
	void ratingList_Ok_Test(){

		// GIVEN
		when(ratingService.findAll()).thenReturn(ratingList);
		when(userService.getLoggedUser()).thenReturn(loggedUser);

		// WHEN
		String result = ratingController.home(model);

		// THEN
		assertEquals("rating/list", result);
	}

	@Test
	void addRatingForm_Ok_Test(){

		// GIVEN
		// WHEN
		String result = ratingController.addRatingForm(rating1);

		// THEN
		assertEquals("rating/add", result);
	}

	@Test
	void validate_Ok_Test(){

		// GIVEN
		when(ratingService.save(any(Rating.class))).thenReturn(rating1);
		when(ratingService.findAll()).thenReturn(ratingList);

		// WHEN
		String result = ratingController.validate(rating1, bindingResult, model);

		// THEN
		assertEquals("redirect:/rating/list", result);
	}

	@Test
	void validate_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = ratingController.validate(rating2, bindingResult, model);

		// THEN
		assertEquals("rating/add", result);
	}

	@Test
	void showUpdateForm_Ok_Test(){

		// GIVEN
		when(ratingService.findById(anyInt())).thenReturn(rating1);

		// WHEN
		String result = ratingController.showUpdateForm(1, model);

		// THEN
		assertEquals("rating/update", result);
	}

	@Test
	void showUpdateForm_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(ratingService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		ratingController.showUpdateForm(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception, rating not found"));

	}

	@Test
	void updateRating_Ok_Test(){

		// GIVEN
		when(ratingService.save(any(Rating.class))).thenReturn(rating1);
		when(ratingService.findAll()).thenReturn(ratingList);

		// WHEN
		String result = ratingController.updateRating(1, rating1, bindingResult, model);

		// THEN
		assertEquals("redirect:/rating/list", result);
	}

	@Test
	void updateRating_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = ratingController.updateRating(1, rating1, bindingResult, model);

		// THEN
		assertEquals("rating/update", result);
	}

	@Test
	void deleteRating_Ok_Test(){

		// GIVEN
		when(ratingService.findById(anyInt())).thenReturn(rating1);
		when(ratingService.findAll()).thenReturn(ratingList);

		// WHEN
		String result = ratingController.deleteRating(1, model);

		// THEN
		assertEquals("redirect:/rating/list", result);
	}

	@Test
	void deleteRating_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(ratingService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		ratingController.deleteRating(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception, rating not found"));

	}
}
