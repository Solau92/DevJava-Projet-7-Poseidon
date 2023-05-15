package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

	private RatingRepository ratingRepository;

	public RatingServiceImpl(RatingRepository ratingRepository){
		this.ratingRepository = ratingRepository;
	}

	@Override
	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public Rating findById(Integer id) {

		Optional<Rating> optionalR = ratingRepository.findById(id);

		if(optionalR.isEmpty()) {
			throw new IllegalArgumentException("Invalid rating Id: " + id);
		}
		return optionalR.get();	}

	@Override
	public void delete(Rating rating) {
		Optional<Rating> optionalR = ratingRepository.findById(rating.getId());
		if(optionalR.isEmpty()) {
			throw new IllegalArgumentException("Invalid rating Id: " + rating.getId());
		}
		ratingRepository.delete(rating);
	}
}
