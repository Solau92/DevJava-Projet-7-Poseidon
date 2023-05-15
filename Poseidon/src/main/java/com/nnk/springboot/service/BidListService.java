package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {


	List<BidList> findAll();

	BidList save(BidList bidList);
}
