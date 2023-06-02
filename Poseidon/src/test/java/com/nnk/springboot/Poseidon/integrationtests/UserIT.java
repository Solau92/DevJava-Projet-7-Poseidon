package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private WebApplicationContext context;

	private User user1 = new User();

	private User user2 = new User();


	@BeforeEach
	void setUp() throws Exception {

		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ADMIN"})
	@Order(1)
	void saveUser_Ok_Test() throws Exception {

		user1.setUsername("username1");
		user1.setPassword("123456A*");
		user1.setFullname("fullname1");
		user1.setRole("user");

		mockMvc.perform(post("/user/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("username", user1.getUsername())
						.param("password", user1.getPassword())
						.param("fullname", user1.getFullname())
						.param("role", user1.getRole())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/user/list"));

		user2.setUsername("username2");
		user2.setPassword("123456A*");
		user2.setFullname("fullname2");
		user2.setRole("user");

		mockMvc.perform(post("/user/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("username", user2.getUsername())
						.param("password", user2.getPassword())
						.param("fullname", user2.getFullname())
						.param("role", user2.getRole())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/user/list"));

		User user1Expected = userService.findByUsername("username1");
		User user2Expected = userService.findByUsername("username2");
		List<User> userList = userService.findAll();

		assertEquals(user1.getUsername(), user1Expected.getUsername());
		assertEquals(user2.getFullname(), user2Expected.getFullname());
		assertEquals(2, userList.size());
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ADMIN"})
	@Order(2)
	void updateUser_Ok_Test() throws Exception {

		user1 = userService.findById(1);

		mockMvc.perform(post("/user/update/{id}", 1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("username", user1.getUsername())
						.param("password", user1.getPassword())
						.param("fullname", "fullname4")
						.param("role", user1.getRole())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/user/list"));

		User user1Expected = userService.findByUsername("username1");
		List<User> userList = userService.findAll();

		assertEquals("fullname4", user1Expected.getFullname());
		assertEquals(2, userList.size());
	}

	@Test
	@WithMockUser(username = "admin", authorities = {"ADMIN"})
	@Order(3)
	void deleteUser_Ok_Test() throws Exception {

		mockMvc.perform(get("/user/delete/{id}", 2)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/user/list"));

		List<User> userList = userService.findAll();
		assertEquals(1, userList.size());
	}

}
