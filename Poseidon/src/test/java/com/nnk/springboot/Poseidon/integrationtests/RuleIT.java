package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.service.implementations.RuleServiceImpl;
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
class RuleIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuleServiceImpl ruleService;

	@Autowired
	private WebApplicationContext context;

	private Rule rule1 = new Rule();

	private Rule rule2 = new Rule();


	@BeforeEach
	void setUp() throws Exception {

		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(username = "user")
	@Order(1)
	void saveRule_Ok_Test() throws Exception {

		rule1.setName("name1");
		rule1.setDescription("description1");
		rule1.setJson("json1");

		mockMvc.perform(post("/rule/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name", rule1.getName())
						.param("description", rule1.getDescription())
						.param("json", rule1.getJson())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rule/list"));

		rule2.setName("name2");
		rule2.setDescription("description2");
		rule2.setJson("json2");

		mockMvc.perform(post("/rule/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name", rule2.getName())
						.param("description", rule2.getDescription())
						.param("json", rule2.getJson())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rule/list"));

		Rule rule1Expected = ruleService.findById(1);
		Rule rule2Expected = ruleService.findById(2);
		List<Rule> ruleList = ruleService.findAll();

		assertEquals(rule1.getName(), rule1Expected.getName());
		assertEquals(rule2.getDescription(), rule2Expected.getDescription());
		assertEquals(2, ruleList.size());
	}

	@Test
	@WithMockUser(username = "user")
	@Order(2)
	void updateRule_Ok_Test() throws Exception {

		rule1 = ruleService.findById(1);

		mockMvc.perform(post("/rule/update/{id}", 1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name", rule1.getName())
						.param("description", "description4")
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rule/list"));

		Rule rule1Expected = ruleService.findById(1);
		List<Rule> ruleList = ruleService.findAll();

		assertEquals("description4", rule1Expected.getDescription());
		assertEquals(2, ruleList.size());
	}

	@Test
	@WithMockUser(username = "user")
	@Order(3)
	void deleteRule_Ok_Test() throws Exception {

		mockMvc.perform(get("/rule/delete/{id}", 2)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rule/list"));

		List<Rule> ruleList = ruleService.findAll();
		assertEquals(1, ruleList.size());
	}

}
