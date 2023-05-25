package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {

	List<Rating> findAll();

	Rating save(Rating rating);

	Rating findById(Integer id);

	void delete(Rating rating);
}
