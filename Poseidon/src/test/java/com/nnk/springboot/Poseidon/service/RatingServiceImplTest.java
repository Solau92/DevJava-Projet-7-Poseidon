package com.nnk.springboot.Poseidon.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.implementation.RatingServiceImpl;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class RatingServiceImplTest {

	@InjectMocks
	private RatingServiceImpl ratingService;

	@Mock
	private RatingRepository ratingRepository;

	private Rating rating1 = new Rating();

	private Rating rating2 = new Rating();

	private List<Rating> ratingList = new ArrayList<>();

	private Optional<Rating> optionalRating;

	private Optional<Rating> emptyOptional;


	@BeforeEach
	void setUp(){

		rating1.setId(1);
		rating1.setMoodysRating("mr1");

		rating2.setId(2);
		rating2.setMoodysRating("mr2");

		ratingList.add(rating1);
		ratingList.add(rating2);

		optionalRating = Optional.of(rating1);

		emptyOptional = Optional.empty();
	}

	@Test
	void findAll_Ok_Test(){

		// GIVEN
		when(ratingRepository.findAll()).thenReturn(ratingList);

		// WHEN
		List<Rating> result = ratingService.findAll();

		// THEN
		assertEquals(rating1, result.get(0));
		assertEquals(rating2, result.get(1));
	}

	@Test
	void save_Ok_Test(){

		// GIVEN
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);

		// WHEN
		Rating expected = ratingService.save(rating1);

		// THEN
		assertEquals(rating1, expected);
	}

	@Test
	void findById_Ok_Test(){

		// GIVEN
		when(ratingRepository.findById(anyInt())).thenReturn(optionalRating);

		// WHEN
		Rating expected = ratingService.findById(1);

		// THEN
		assertEquals(rating1, expected);
	}

	@Test
	void findById_Error_Test(){

		// GIVEN
		when(ratingRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> ratingService.findById(5));
	}

	@Test
	void delete_Ok_Test(){

		// GIVEN
		when(ratingRepository.findById(anyInt())).thenReturn(optionalRating);

		// WHEN
		ratingService.delete(rating1);

		// THEN
		verify(ratingRepository, Mockito.times(1)).delete(rating1);
	}

	@Test
	void delete_Error_Test(CapturedOutput output){

		// GIVEN
		when(ratingRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> ratingService.delete(rating1));

	}

}
