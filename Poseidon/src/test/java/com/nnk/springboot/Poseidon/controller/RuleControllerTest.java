package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controller.RuleController;
import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementations.RuleServiceImpl;
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
public class RuleControllerTest {

	@InjectMocks
	private RuleController ruleController;

	@Mock
	private RuleServiceImpl ruleService;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	private User loggedUser = new User();

	private Rule rule1 = new Rule();

	private Rule rule2 = new Rule();

	private List<Rule> ruleList = new ArrayList<>();


	@BeforeEach
	void setUp(){

		loggedUser.setUsername("userName");
		loggedUser.setRole("USER");

		rule1.setId(1);
		rule1.setName("name1");
		rule1.setDescription("Description1");

		rule1.setId(2);
		rule2.setName(null);
		rule2.setDescription(null);

		ruleList.add(rule1);
		ruleList.add(rule2);
	}


	@Test
	void ruleList_Ok_Test(){

		// GIVEN
		when(ruleService.findAll()).thenReturn(ruleList);
		when(userService.getLoggedUser()).thenReturn(loggedUser);

		// WHEN
		String result = ruleController.home(model);

		// THEN
		assertEquals("rule/list", result);
	}

	@Test
	void addRuleForm_Ok_Test(){

		// GIVEN
		// WHEN
		String result = ruleController.addRuleForm(rule1);

		// THEN
		assertEquals("rule/add", result);
	}

	@Test
	void validate_Ok_Test(){

		// GIVEN
		when(ruleService.save(any(Rule.class))).thenReturn(rule1);
		when(ruleService.findAll()).thenReturn(ruleList);

		// WHEN
		String result = ruleController.validate(rule1, bindingResult, model);

		// THEN
		assertEquals("redirect:/rule/list", result);
	}

	@Test
	void validate_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = ruleController.validate(rule2, bindingResult, model);

		// THEN
		assertEquals("rule/add", result);
	}

	@Test
	void showUpdateForm_Ok_Test(){

		// GIVEN
		when(ruleService.findById(anyInt())).thenReturn(rule1);

		// WHEN
		String result = ruleController.showUpdateForm(1, model);

		// THEN
		assertEquals("rule/update", result);
	}

	@Test
	void showUpdateForm_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(ruleService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		ruleController.showUpdateForm(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception, rule not found"));

	}

	@Test
	void updateRule_Ok_Test(){

		// GIVEN
		when(ruleService.save(any(Rule.class))).thenReturn(rule1);
		when(ruleService.findAll()).thenReturn(ruleList);

		// WHEN
		String result = ruleController.updateRule(1, rule1, bindingResult, model);

		// THEN
		assertEquals("redirect:/rule/list", result);
	}

	@Test
	void updateRule_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = ruleController.updateRule(1, rule1, bindingResult, model);

		// THEN
		assertEquals("rule/update", result);
	}

	@Test
	void deleteRule_Ok_Test(){

		// GIVEN
		when(ruleService.findById(anyInt())).thenReturn(rule1);
		when(ruleService.findAll()).thenReturn(ruleList);

		// WHEN
		String result = ruleController.deleteRule(1, model);

		// THEN
		assertEquals("redirect:/rule/list", result);
	}

	@Test
	void deleteRule_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(ruleService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		ruleController.deleteRule(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception, rule not found"));

	}


}
