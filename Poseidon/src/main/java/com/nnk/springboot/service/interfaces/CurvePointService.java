package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {


	/**
	 * Returns all the curve points.
	 *
	 * @return a List<CurvePoint>
	 */
	List<CurvePoint> findAll();

	/**
	 * Saves the given curve point.
	 *
	 * @param curvePoint
	 * @return the curve point saved
	 */
	CurvePoint save(CurvePoint curvePoint);

	/**
	 * Searches a curve point given an id.
	 *
	 * @param id
	 * @return the curve point if found
	 * @throws IllegalArgumentException if the curve point was not found
	 */
	CurvePoint findById(Integer id);

	/**
	 * Deletes the given curve point.
	 *
	 * @param curvePoint
	 * @throws IllegalArgumentException if the curve point was not found
	 */
	void delete(CurvePoint curvePoint);
}
