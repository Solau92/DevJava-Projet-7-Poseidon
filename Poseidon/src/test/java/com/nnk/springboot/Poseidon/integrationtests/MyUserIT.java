package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
@SpringBootTest
class MyUserIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	void setUpEach() {

		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

		userRepository.deleteAll();
	}

	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	void addUser_Ok_Test() throws Exception {

		User userToSave = new User();
		userToSave.setUsername("username");
		userToSave.setFullname("fullname");
		userToSave.setPassword("123456A*");
		userToSave.setRole("USER");

		mockMvc.perform(post("/user/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("username", userToSave.getUsername())
						.param("fullname", userToSave.getFullname())
						.param("role", userToSave.getRole())
						.param("password", userToSave.getPassword())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/user/list"));


	}

}
