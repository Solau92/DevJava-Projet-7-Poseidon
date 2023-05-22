package com.nnk.springboot.Poseidon.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointServiceImpl;
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
public class CurvePointServiceImplTest {

	@InjectMocks
	private CurvePointServiceImpl curvePointService;

	@Mock
	private CurvePointRepository curvePointRepository;

	private CurvePoint curvePoint1 = new CurvePoint();

	private CurvePoint curvePoint2 = new CurvePoint();

	private List<CurvePoint> curvePointList = new ArrayList<>();

	private Optional<CurvePoint> optionalCurvePoint;

	private Optional<CurvePoint> emptyOptional;


	@BeforeEach
	void setUp(){

		curvePoint1.setId(1);
		curvePoint1.setCurveId(11);

		curvePoint2.setId(2);
		curvePoint2.setCurveId(22);

		curvePointList.add(curvePoint1);
		curvePointList.add(curvePoint2);

		optionalCurvePoint = Optional.of(curvePoint1);

		emptyOptional = Optional.empty();
	}

	@Test
	void findAll_Ok_Test(){

		// GIVEN
		when(curvePointRepository.findAll()).thenReturn(curvePointList);

		// WHEN
		List<CurvePoint> result = curvePointService.findAll();

		// THEN
		assertEquals(curvePoint1, result.get(0));
		assertEquals(curvePoint2, result.get(1));
	}

	@Test
	void save_Ok_Test(){

		// GIVEN
		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1);

		// WHEN
		CurvePoint expected = curvePointService.save(curvePoint1);

		// THEN
		assertEquals(curvePoint1, expected);
	}

	@Test
	void findById_Ok_Test(){

		// GIVEN
		when(curvePointRepository.findById(anyInt())).thenReturn(optionalCurvePoint);

		// WHEN
		CurvePoint expected = curvePointService.findById(1);

		// THEN
		assertEquals(curvePoint1, expected);
	}

	@Test
	void findById_Error_Test(){

		// GIVEN
		when(curvePointRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> curvePointService.findById(5));
	}

	@Test
	void delete_Ok_Test(){

		// GIVEN
		when(curvePointRepository.findById(anyInt())).thenReturn(optionalCurvePoint);

		// WHEN
		curvePointService.delete(curvePoint1);

		// THEN
		verify(curvePointRepository, Mockito.times(1)).delete(curvePoint1);
	}

	@Test
	void delete_Error_Test(){

		// GIVEN
		when(curvePointRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> curvePointService.findById(5));

	}

}
