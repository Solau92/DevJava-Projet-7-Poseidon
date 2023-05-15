package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Bid;

import java.util.List;

public interface BidService {


	List<Bid> findAll();

	Bid save(Bid bidList);
}
