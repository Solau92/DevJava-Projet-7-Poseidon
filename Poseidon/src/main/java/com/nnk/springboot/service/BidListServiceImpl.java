package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService {

	private BidListRepository bidListRepository;

	public BidListServiceImpl(BidListRepository bidListRepository) {
		this.bidListRepository = bidListRepository;
	}

	@Override
	public List<BidList> findAll() {
		return bidListRepository.findAll();
	}

	@Override
	public BidList save(BidList bidList) {
		return bidListRepository.save(bidList);
	}

	public BidList findById(Integer id) {

		Optional<BidList> optionalBL = bidListRepository.findById(id);

		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid bidList Id: " + id);
		}
		return optionalBL.get();

	}

	public void delete(BidList bidList) {
		Optional<BidList> optionalBL = bidListRepository.findById(bidList.getId());
		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid bidList Id: " + bidList.getId());
		}
		bidListRepository.delete(bidList);
	}
}
