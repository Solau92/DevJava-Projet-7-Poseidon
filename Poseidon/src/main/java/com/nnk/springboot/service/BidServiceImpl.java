package com.nnk.springboot.service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.service.interfaces.BidService;
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
	public Bid save(Bid bid) {
		return bidRepository.save(bid);
	}

	public Bid findById(Integer id) {

		Optional<Bid> optionalBL = bidRepository.findById(id);

		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid bid Id: " + id);
		}
		return optionalBL.get();
	}

	public void delete(Bid bid) {
		Optional<Bid> optionalBL = bidRepository.findById(bid.getId());
		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid bid Id: " + bid.getId());
		}
		bidRepository.delete(bid);
	}
}
