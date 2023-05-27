package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controller.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementations.CurvePointServiceImpl;
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
class CurvePointControllerTest {

	@InjectMocks
	private CurvePointController curvePointController;

	@Mock
	private CurvePointServiceImpl curvePointService;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	private User loggedUser = new User();

	private CurvePoint curvePoint1 = new CurvePoint();

	private CurvePoint curvePoint2 = new CurvePoint();

	private List<CurvePoint> curvePointList = new ArrayList<>();

	@BeforeEach
	void setUp(){

		loggedUser.setUsername("userName");
		loggedUser.setRole("USER");

		curvePoint1.setId(1);
		curvePoint1.setCurveId(11);

		curvePoint2.setId(2);
		curvePoint2.setCurveId(22);

		curvePointList.add(curvePoint1);
		curvePointList.add(curvePoint2);
	}
	@Test
	void curvePointList_Ok_Test(){

		// GIVEN
		when(curvePointService.findAll()).thenReturn(curvePointList);
		when(userService.getLoggedUser()).thenReturn(loggedUser);

		// WHEN
		String result = curvePointController.home(model);

		// THEN
		assertEquals("curvePoint/list", result);
	}

	@Test
	void addCurvePointForm_Ok_Test(){

		// GIVEN
		// WHEN
		String result = curvePointController.addBidForm(curvePoint1);

		// THEN
		assertEquals("curvePoint/add", result);
	}

	@Test
	void validate_Ok_Test(){

		// GIVEN
		when(curvePointService.save(any(CurvePoint.class))).thenReturn(curvePoint1);
		when(curvePointService.findAll()).thenReturn(curvePointList);

		// WHEN
		String result = curvePointController.validate(curvePoint1, bindingResult, model);

		// THEN
		assertEquals("redirect:/curvePoint/list", result);
	}

	@Test
	void validate_ResultHasError_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = curvePointController.validate(curvePoint2, bindingResult, model);

		// THEN
		assertEquals("curvePoint/add", result);
	}

	@Test
	void showUpdateForm_Ok_Test(){

		// GIVEN
		when(curvePointService.findById(anyInt())).thenReturn(curvePoint1);

		// WHEN
		String result = curvePointController.showUpdateForm(1, model);

		// THEN
		assertEquals("curvePoint/update", result);
	}

	@Test
	void showUpdateForm_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(curvePointService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		curvePointController.showUpdateForm(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception"));

	}

	@Test
	void updateCurvePoint_Ok_Test(){

		// GIVEN
		when(curvePointService.save(any(CurvePoint.class))).thenReturn(curvePoint1);
		when(curvePointService.findAll()).thenReturn(curvePointList);

		// WHEN
		String result = curvePointController.updateBid(1, curvePoint1, bindingResult, model);

		// THEN
		assertEquals("redirect:/curvePoint/list", result);
	}

	@Test
	void updateCurvePoint_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = curvePointController.updateBid(1, curvePoint1, bindingResult, model);

		// THEN
		assertEquals("curvePoint/update", result);
	}

	@Test
	void deleteCurvePoint_Ok_Test(){

		// GIVEN
		when(curvePointService.findById(anyInt())).thenReturn(curvePoint1);
		when(curvePointService.findAll()).thenReturn(curvePointList);

		// WHEN
		String result = curvePointController.deleteCurvePoint(1, model);

		// THEN
		assertEquals("redirect:/curvePoint/list", result);
	}

	@Test
	void deleteCurvePoint_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(curvePointService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		curvePointController.deleteCurvePoint(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception"));

	}
}
