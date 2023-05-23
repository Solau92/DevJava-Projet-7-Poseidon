package com.nnk.springboot.Poseidon.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
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
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	private User loggedUser = new User();

	private User user1 = new User();

	private User user2 = new User();

	private List<User> userList = new ArrayList<>();


	@BeforeEach
	void setUp(){

		loggedUser.setUsername("userName");
		loggedUser.setRole("ADMIN");

		user1.setId(1);
		user1.setUsername("username1");
		user1.setFullname("fullname1");

		user1.setId(2);
		user1.setUsername("username2");
		user1.setFullname("fullname2");

		userList.add(user1);
		userList.add(user2);
	}


	@Test
	void userList_Ok_Test(){

		// GIVEN
		when(userService.findAll()).thenReturn(userList);
		when(userService.getLoggedUser()).thenReturn(loggedUser);

		// WHEN
		String result = userController.home(model);

		// THEN
		assertEquals("user/list", result);
	}

	@Test
	void addUserForm_Ok_Test(){

		// GIVEN
		// WHEN
		String result = userController.addUserForm(user1);

		// THEN
		assertEquals("user/add", result);
	}

	@Test
	void validate_Ok_Test(){

		// GIVEN
		when(userService.save(any(User.class))).thenReturn(user1);
		when(userService.findAll()).thenReturn(userList);

		// WHEN
		String result = userController.validate(user1, bindingResult, model);

		// THEN
		assertEquals("redirect:/user/list", result);
	}

	@Test
	void validate_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = userController.validate(user2, bindingResult, model);

		// THEN
		assertEquals("user/add", result);
	}

	@Test
	void showUpdateForm_Ok_Test(){

		// GIVEN
		when(userService.findById(anyInt())).thenReturn(user1);

		// WHEN
		String result = userController.showUpdateForm(1, model);

		// THEN
		assertEquals("user/update", result);
	}

	@Test
	void showUpdateForm_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(userService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		userController.showUpdateForm(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception, user not found"));

	}

	@Test
	void updateUser_Ok_Test(){

		// GIVEN
		when(userService.save(any(User.class))).thenReturn(user1);
		when(userService.findAll()).thenReturn(userList);

		// WHEN
		String result = userController.updateUser(1, user1, bindingResult, model);

		// THEN
		assertEquals("redirect:/user/list", result);
	}

	@Test
	void updateUser_Error_Test(){

		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String result = userController.updateUser(1, user1, bindingResult, model);

		// THEN
		assertEquals("user/update", result);
	}

	@Test
	void deleteUser_Ok_Test(){

		// GIVEN
		when(userService.findById(anyInt())).thenReturn(user1);
		when(userService.findAll()).thenReturn(userList);

		// WHEN
		String result = userController.deleteUser(1, model);

		// THEN
		assertEquals("redirect:/user/list", result);
	}

	@Test
	void deleteUser_IllegalArgumentException_Test(CapturedOutput output){

		// GIVEN
		when(userService.findById(anyInt())).thenThrow(IllegalArgumentException.class);

		// WHEN
		userController.deleteUser(5, model);

		// THEN
		assertTrue(output.toString().contains("Illegal Argument Exception, user not found"));

	}
}
