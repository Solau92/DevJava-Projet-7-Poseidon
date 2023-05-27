package com.nnk.springboot.Poseidon.service;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repository.RuleRepository;
import com.nnk.springboot.service.implementations.RuleServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
class RuleServiceImplTest {

	@InjectMocks
	private RuleServiceImpl ruleService;

	@Mock
	private RuleRepository ruleRepository;

	private Rule rule1 = new Rule();

	private Rule rule2 = new Rule();

	private List<Rule> ruleList = new ArrayList<>();

	private Optional<Rule> optionalRule;

	private Optional<Rule> emptyOptional;


	@BeforeEach
	void setUp(){

		rule1.setId(1);
		rule1.setName("name1");
		rule1.setDescription("Description1");

		rule1.setId(2);
		rule2.setName(null);
		rule2.setDescription(null);

		ruleList.add(rule1);
		ruleList.add(rule2);

		optionalRule = Optional.of(rule1);

		emptyOptional = Optional.empty();
	}

	@Test
	void findAll_Ok_Test(){

		// GIVEN
		when(ruleRepository.findAll()).thenReturn(ruleList);

		// WHEN
		List<Rule> result = ruleService.findAll();

		// THEN
		assertEquals(rule1, result.get(0));
		assertEquals(rule2, result.get(1));
	}

	@Test
	void save_Ok_Test(){

		// GIVEN
		when(ruleRepository.save(any(Rule.class))).thenReturn(rule1);

		// WHEN
		Rule expected = ruleService.save(rule1);

		// THEN
		assertEquals(rule1, expected);
	}

	@Test
	void findById_Ok_Test(){

		// GIVEN
		when(ruleRepository.findById(anyInt())).thenReturn(optionalRule);

		// WHEN
		Rule expected = ruleService.findById(1);

		// THEN
		assertEquals(rule1, expected);
	}

	@Test
	void findById_Error_Test(){

		// GIVEN
		when(ruleRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> ruleService.findById(5));
	}

	@Test
	void delete_Ok_Test(){

		// GIVEN
		when(ruleRepository.findById(anyInt())).thenReturn(optionalRule);

		// WHEN
		ruleService.delete(rule1);

		// THEN
		verify(ruleRepository, Mockito.times(1)).delete(rule1);
	}

	@Test
	void delete_Error_Test(){

		// GIVEN
		when(ruleRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> ruleService.delete(rule1));

	}
}
