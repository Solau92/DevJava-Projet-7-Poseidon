package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {


	List<CurvePoint> findAll();

	CurvePoint save(CurvePoint curvePoint);

	CurvePoint finById(Integer id);

	void delete(CurvePoint curvePoint);
}
