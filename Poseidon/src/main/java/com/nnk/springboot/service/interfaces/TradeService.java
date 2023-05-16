package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
	List<Trade> findAll();

	Trade save(Trade trade);

	Trade findById(Integer id);

	void delete(Trade trade);
}
