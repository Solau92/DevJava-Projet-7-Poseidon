package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.implementations.RatingServiceImpl;
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
class RatingIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RatingServiceImpl ratingService;

	@Autowired
	private WebApplicationContext context;

	private Rating rating1 = new Rating();

	private Rating rating2 = new Rating();


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
	void saveRating_Ok_Test() throws Exception {

		rating1.setMoodysRating("mr1");
		rating1.setSAndPRating("spr1");
		rating1.setFitchRating("fr1");

		mockMvc.perform(post("/rating/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("moodysRating", rating1.getMoodysRating())
						.param("sAndPRating", rating1.getSAndPRating())
						.param("fitchRating", rating1.getFitchRating())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rating/list"));

		rating2.setMoodysRating("mr2");
		rating2.setSAndPRating("spr2");
		rating2.setFitchRating("fr2");

		mockMvc.perform(post("/rating/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("moodysRating", rating2.getMoodysRating())
						.param("sAndPRating", rating2.getSAndPRating())
						.param("fitchRating", rating2.getFitchRating())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rating/list"));

		Rating rating1Expected = ratingService.findById(1);
		Rating rating2Expected = ratingService.findById(2);
		List<Rating> ratingList = ratingService.findAll();

		assertEquals(rating1.getSAndPRating(), rating1Expected.getSAndPRating());
		assertEquals(rating2.getFitchRating(), rating2Expected.getFitchRating());
		assertEquals(2, ratingList.size());
	}

	@Test
	@WithMockUser(username = "user")
	@Order(2)
	void updateRating_Ok_Test() throws Exception {

		rating1 = ratingService.findById(1);

		mockMvc.perform(post("/rating/update/{id}", 1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("moodysRating", rating1.getMoodysRating())
						.param("sAndPRating", rating1.getSAndPRating())
						.param("fitchRating", "fr4")
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rating/list"));

		Rating rating1Expected = ratingService.findById(1);
		List<Rating> ratingList = ratingService.findAll();

		assertEquals("fr4", rating1Expected.getFitchRating());
		assertEquals(2, ratingList.size());
	}

	@Test
	@WithMockUser(username = "user")
	@Order(3)
	void deleteRating_Ok_Test() throws Exception {

		mockMvc.perform(get("/rating/delete/{id}", 2)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/rating/list"));

		List<Rating> ratingList = ratingService.findAll();
		assertEquals(1, ratingList.size());
	}

}
