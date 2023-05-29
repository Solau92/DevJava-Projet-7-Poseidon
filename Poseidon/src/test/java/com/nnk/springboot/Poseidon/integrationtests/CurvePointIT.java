package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.implementations.CurvePointServiceImpl;
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
class CurvePointIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CurvePointServiceImpl curvePointService;

	@Autowired
	private WebApplicationContext context;

	private CurvePoint curvePoint1 = new CurvePoint();

	private CurvePoint curvePoint2 = new CurvePoint();


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
	void saveCurvePoint_Ok_Test() throws Exception {

		curvePoint1.setCurveId(11);
		curvePoint1.setTerm(1.0);
		curvePoint1.setCpvalue(1.0);

		mockMvc.perform(post("/curvePoint/validate")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("curveId", curvePoint1.getCurveId().toString())
						.param("term", curvePoint1.getTerm().toString())
						.param("curvePointQuantity", curvePoint1.getCpvalue().toString())
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/curvePoint/list"));

		curvePoint2.setCurveId(22);
		curvePoint2.setTerm(2.0);
		curvePoint2.setCpvalue(2.0);

		mockMvc.perform(post("/curvePoint/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("curveId", curvePoint2.getCurveId().toString())
				.param("term", curvePoint2.getTerm().toString())
				.param("curvePointQuantity", curvePoint2.getCpvalue().toString())
				.with(csrf())
		);

		CurvePoint curvePoint1Expected = curvePointService.findById(1);
		CurvePoint curvePoint2Expected = curvePointService.findById(2);
		List<CurvePoint> curvePointList = curvePointService.findAll();

		assertEquals(curvePoint1.getCurveId(), curvePoint1Expected.getCurveId());
		assertEquals(curvePoint2.getTerm(), curvePoint2Expected.getTerm());
		assertEquals(2, curvePointList.size());
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	@Order(2)
	void updateCurvePoint_Ok_Test() throws Exception {

		curvePoint1 = curvePointService.findById(1);

		mockMvc.perform(post("/curvePoint/update/{id}", 1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("account", curvePoint1.getCurveId().toString())
						.param("cpvalue", String.valueOf(4))
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/curvePoint/list"));

		CurvePoint curvePoint1Expected = curvePointService.findById(1);
		List<CurvePoint> curvePointList = curvePointService.findAll();

		assertEquals(4, curvePoint1Expected.getCpvalue());
		assertEquals(2, curvePointList.size());
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	@Order(3)
	void deleteCurvePoint_Ok_Test() throws Exception {

		mockMvc.perform(get("/curvePoint/delete/{id}", 2)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.with(csrf())
				)
				.andDo(print())
				.andExpect(redirectedUrl("/curvePoint/list"));

		List<CurvePoint> curvePointList = curvePointService.findAll();
		assertEquals(1, curvePointList.size());
	}

}
