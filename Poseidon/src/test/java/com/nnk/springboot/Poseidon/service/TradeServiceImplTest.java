package com.nnk.springboot.Poseidon.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.implementations.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
public class TradeServiceImplTest {

	@InjectMocks
	private TradeServiceImpl tradeService;

	@Mock
	private TradeRepository tradeRepository;

	private Trade trade1 = new Trade();

	private Trade trade2 = new Trade();

	private List<Trade> tradeList = new ArrayList<>();

	private Optional<Trade> optionalTrade;

	private Optional<Trade> emptyOptional;


	@BeforeEach
	void setUp(){

		trade1.setId(1);
		trade1.setAccount("account1");
		trade1.setType("type1");

		trade2.setAccount(null);
		trade2.setType(null);

		tradeList.add(trade1);
		tradeList.add(trade2);

		optionalTrade = Optional.of(trade1);

		emptyOptional = Optional.empty();
	}

	@Test
	void findAll_Ok_Test(){

		// GIVEN
		when(tradeRepository.findAll()).thenReturn(tradeList);

		// WHEN
		List<Trade> result = tradeService.findAll();

		// THEN
		assertEquals(trade1, result.get(0));
		assertEquals(trade2, result.get(1));
	}

	@Test
	void save_Ok_Test(){

		// GIVEN
		when(tradeRepository.save(any(Trade.class))).thenReturn(trade1);

		// WHEN
		Trade expected = tradeService.save(trade1);

		// THEN
		assertEquals(trade1, expected);
	}

	@Test
	void findById_Ok_Test(){

		// GIVEN
		when(tradeRepository.findById(anyInt())).thenReturn(optionalTrade);

		// WHEN
		Trade expected = tradeService.findById(1);

		// THEN
		assertEquals(trade1, expected);
	}

	@Test
	void findById_Error_Test(){

		// GIVEN
		when(tradeRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> tradeService.findById(5));
	}

	@Test
	void delete_Ok_Test(){

		// GIVEN
		when(tradeRepository.findById(anyInt())).thenReturn(optionalTrade);

		// WHEN
		tradeService.delete(trade1);

		// THEN
		verify(tradeRepository, Mockito.times(1)).delete(trade1);
	}

	@Test
	void delete_Error_Test(){

		// GIVEN
		when(tradeRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> tradeService.delete(trade1));

	}
}
