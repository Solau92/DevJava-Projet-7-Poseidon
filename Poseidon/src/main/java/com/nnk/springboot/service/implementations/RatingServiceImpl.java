package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.interfaces.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {

	private RatingRepository ratingRepository;

	public RatingServiceImpl(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	/**
	 * Returns all the ratings in database.
	 *
	 * @return a List<Rating>
	 */
	@Override
	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}

	/**
	 * Saves the given rating in database.
	 *
	 * @param rating
	 * @return the rating saved
	 */
	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

	/**
	 * Searches in database a trade given an id.
	 *
	 * @param id
	 * @return the rating if found
	 * @throws IllegalArgumentException if the rating was not found
	 */
	@Override
	public Rating findById(Integer id) {

		Optional<Rating> optionalR = ratingRepository.findById(id);

		if (optionalR.isEmpty()) {
			log.error("rating with id " + id + " not found");
			throw new IllegalArgumentException("Invalid rating Id: " + id);
		}
		return optionalR.get();
	}

	/**
	 * Deletes in database the given rating.
	 *
	 * @param rating
	 * @throws IllegalArgumentException if the rating was not found
	 */
	@Override
	public void delete(Rating rating) {

		Optional<Rating> optionalR = ratingRepository.findById(rating.getId());
		if (optionalR.isEmpty()) {
			log.error("rating with id " + rating.getId() + " not found");
			throw new IllegalArgumentException("Invalid rating Id: " + rating.getId());
		}
		ratingRepository.delete(rating);
	}
}
