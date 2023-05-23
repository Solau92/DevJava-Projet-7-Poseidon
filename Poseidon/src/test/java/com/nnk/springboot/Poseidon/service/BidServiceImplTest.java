package com.nnk.springboot.Poseidon.service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.service.implementation.BidServiceImpl;
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
public class BidServiceImplTest {

	@InjectMocks
	private BidServiceImpl bidService;

	@Mock
	private BidRepository bidRepository;

	private Bid bid1 = new Bid();

	private Bid bid2 = new Bid();

	private List<Bid> bidList = new ArrayList<>();

	private Optional<Bid> optionalBid;

	private Optional<Bid> emptyOptional;


	@BeforeEach
	void setUp(){

		bid1.setId(1);
		bid1.setAccount("account1");
		bid1.setType("type1");

		bid2.setAccount("account2");
		bid2.setType("type2");

		bidList.add(bid1);
		bidList.add(bid2);

		optionalBid = Optional.of(bid1);

		emptyOptional = Optional.empty();
	}

	@Test
	void findAll_Ok_Test(){

		// GIVEN
		when(bidRepository.findAll()).thenReturn(bidList);

		// WHEN
		List<Bid> result = bidService.findAll();

		// THEN
		assertEquals(bid1, result.get(0));
		assertEquals(bid2, result.get(1));
	}

	@Test
	void save_Ok_Test(){

		// GIVEN
		when(bidRepository.save(any(Bid.class))).thenReturn(bid1);

		// WHEN
		Bid expected = bidService.save(bid1);

		// THEN
		assertEquals(bid1, expected);
	}

	@Test
	void findById_Ok_Test(){

		// GIVEN
		when(bidRepository.findById(anyInt())).thenReturn(optionalBid);

		// WHEN
		Bid expected = bidService.findById(1);

		// THEN
		assertEquals(bid1, expected);
	}

	@Test
	void findById_Error_Test(){

		// GIVEN
		when(bidRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> bidService.findById(5));
	}

	@Test
	void delete_Ok_Test(){

		// GIVEN
		when(bidRepository.findById(anyInt())).thenReturn(optionalBid);

		// WHEN
		bidService.delete(bid1);

		// THEN
		verify(bidRepository, Mockito.times(1)).delete(bid1);
	}

	@Test
	void delete_Error_Test(){

		// GIVEN
		when(bidRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> bidService.delete(bid1));

	}

}
