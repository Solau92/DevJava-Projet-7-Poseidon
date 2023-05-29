package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.implementations.TradeServiceImpl;
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
class TradeIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TradeServiceImpl tradeService;

	@Autowired
	private WebApplicationContext context;

	private Trade trade1 = new Trade();

	private Trade trade2 = new Trade();


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
	void saveTrade_Ok_Test() throws Exception {

		trade1.setAccount("account1");
		trade1.setType("type1");
		trade1.setBuyQuantity(1.0);

		mockMvc.perform(post("/trade/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", trade1.getAccount())
						.param("type", trade1.getType())
						.param("buyQuantity", trade1.getBuyQuantity().toString())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/trade/list"));

		trade2.setAccount("account2");
		trade2.setType("type2");
		trade2.setBuyQuantity(2.0);

		mockMvc.perform(post("/trade/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", trade2.getAccount())
						.param("type", trade2.getType())
						.param("buyQuantity", trade2.getBuyQuantity().toString())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/trade/list"));

		Trade trade1Expected = tradeService.findById(1);
		Trade trade2Expected = tradeService.findById(2);
		List<Trade> tradeList = tradeService.findAll();

		assertEquals(trade1.getAccount(), trade1Expected.getAccount());
		assertEquals(trade2.getType(), trade2Expected.getType());
		assertEquals(2, tradeList.size());
	}

	@Test
	@WithMockUser(username = "user")
	@Order(2)
	void updateTrade_Ok_Test() throws Exception {

		trade1 = tradeService.findById(1);

		mockMvc.perform(post("/trade/update/{id}", 1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", trade1.getAccount())
						.param("type", trade1.getType())
						.param("buyQuantity", String.valueOf(4))
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/trade/list"));

		Trade trade1Expected = tradeService.findById(1);
		List<Trade> tradeList = tradeService.findAll();

		assertEquals(4, trade1Expected.getBuyQuantity());
		assertEquals(2, tradeList.size());
	}

	@Test
	@WithMockUser(username = "user")
	@Order(3)
	void deleteTrade_Ok_Test() throws Exception {

		mockMvc.perform(get("/trade/delete/{id}", 2)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/trade/list"));

		List<Trade> tradeList = tradeService.findAll();
		assertEquals(1, tradeList.size());
	}

}
