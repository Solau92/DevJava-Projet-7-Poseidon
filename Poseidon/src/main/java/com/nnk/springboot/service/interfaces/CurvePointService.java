package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {


	List<CurvePoint> findAll();

	CurvePoint save(CurvePoint curvePoint);

	CurvePoint findById(Integer id);

	void delete(CurvePoint curvePoint);
}
