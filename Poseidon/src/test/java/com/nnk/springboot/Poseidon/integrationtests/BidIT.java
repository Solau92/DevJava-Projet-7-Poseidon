package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.service.implementations.BidServiceImpl;
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
class BidIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidServiceImpl bidService;

	@Autowired
	private WebApplicationContext context;

	private Bid bid1 = new Bid();

	private Bid bid2 = new Bid();


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
	void saveBid_Ok_Test() throws Exception {

		bid1.setAccount("account1");
		bid1.setType("type1");
		bid1.setBidQuantity(1.0);

		mockMvc.perform(post("/bid/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", bid1.getAccount())
						.param("type", bid1.getType())
						.param("bidQuantity", bid1.getBidQuantity().toString())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/bid/list"));

		bid2.setAccount("account2");
		bid2.setType("type2");
		bid2.setBidQuantity(2.0);

		mockMvc.perform(post("/bid/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("account", bid2.getAccount())
				.param("type", bid2.getType())
				.param("bidQuantity", bid2.getBidQuantity().toString())
				.with(csrf())
		);

		Bid bid1Expected = bidService.findById(1);
		Bid bid2Expected = bidService.findById(2);
		List<Bid> bidList = bidService.findAll();

		assertEquals(bid1.getAccount(), bid1Expected.getAccount());
		assertEquals(bid2.getType(), bid2Expected.getType());
		assertEquals(2, bidList.size());
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	@Order(2)
	void updateBid_Ok_Test() throws Exception {

		bid1 = bidService.findById(1);

		mockMvc.perform(post("/bid/update/{id}", 1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", bid1.getAccount())
						.param("type", bid1.getType())
						.param("bidQuantity", String.valueOf(4))
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/bid/list"));

		Bid bid1Expected = bidService.findById(1);
		List<Bid> bidList = bidService.findAll();

		assertEquals(4, bid1Expected.getBidQuantity());
		assertEquals(2, bidList.size());
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	@Order(3)
	void deleteBid_Ok_Test() throws Exception {

		mockMvc.perform(get("/bid/delete/{id}", 2)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/bid/list"));

		List<Bid> bidList = bidService.findAll();
		assertEquals(1, bidList.size());
	}

}
