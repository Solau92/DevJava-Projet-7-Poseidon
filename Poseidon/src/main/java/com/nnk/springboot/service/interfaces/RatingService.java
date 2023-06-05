package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {

	/**
	 * Returns all the ratings.
	 *
	 * @return a List<Rating>
	 */
	List<Rating> findAll();

	/**
	 * Saves the given rating.
	 *
	 * @param rating
	 * @return the rating saved
	 */
	Rating save(Rating rating);

	/**
	 * Searches a trade given an id.
	 *
	 * @param id
	 * @return the rating if found
	 * @throws IllegalArgumentException if the rating was not found
	 */
	Rating findById(Integer id);

	/**
	 * Deletes the given rating.
	 *
	 * @param rating
	 * @throws IllegalArgumentException if the rating was not found
	 */
	void delete(Rating rating);
}
