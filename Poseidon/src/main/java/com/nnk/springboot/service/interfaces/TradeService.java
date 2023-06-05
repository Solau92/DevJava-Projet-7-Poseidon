package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {

	/**
	 * Returns all the trades.
	 *
	 * @return a List<Trade>
	 */
	List<Trade> findAll();

	/**
	 * Saves the given trade.
	 *
	 * @param trade
	 * @return the trade saved
	 */
	Trade save(Trade trade);

	/**
	 * Searches a trade given an id.
	 *
	 * @param id
	 * @return the trade if found
	 * @throws IllegalArgumentException if the trade was not found
	 */
	Trade findById(Integer id);

	/**
	 * Deletes the given trade.
	 *
	 * @param trade
	 * @throws IllegalArgumentException if the trade was not found
	 */
	void delete(Trade trade);
}
