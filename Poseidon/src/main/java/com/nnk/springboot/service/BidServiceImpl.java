package com.nnk.springboot.service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

	private BidRepository bidRepository;

	public BidServiceImpl(BidRepository bidRepository) {
		this.bidRepository = bidRepository;
	}

	@Override
	public List<Bid> findAll() {
		return bidRepository.findAll();
	}

	@Override
	public Bid save(Bid bidList) {
		return bidRepository.save(bidList);
	}

	public Bid findById(Integer id) {

		Optional<Bid> optionalBL = bidRepository.findById(id);

		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid bidList Id: " + id);
		}
		return optionalBL.get();

	}

	public void delete(Bid bidList) {
		Optional<Bid> optionalBL = bidRepository.findById(bidList.getId());
		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid bidList Id: " + bidList.getId());
		}
		bidRepository.delete(bidList);
	}
}
