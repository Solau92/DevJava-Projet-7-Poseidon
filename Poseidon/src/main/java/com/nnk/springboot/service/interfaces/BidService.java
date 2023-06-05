package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Bid;

import java.util.List;

public interface BidService {


	/**
	 * Returns all the bids.
	 *
	 * @return a List<Bid>
	 */
	List<Bid> findAll();

	/**
	 * Saves the given bid.
	 *
	 * @param bid
	 * @return the bid saved
	 */
	Bid save(Bid bid);

	/**
	 * Searches a bid given an id.
	 *
	 * @param id
	 * @return the bid if found
	 * @throws IllegalArgumentException if the bid was not found
	 */
	Bid findById(Integer id) throws IllegalArgumentException;

	/**
	 * Deletes the given bid.
	 *
	 * @param bid
	 * @throws IllegalArgumentException if the bid was not found
	 */
	void delete(Bid bid);
}
