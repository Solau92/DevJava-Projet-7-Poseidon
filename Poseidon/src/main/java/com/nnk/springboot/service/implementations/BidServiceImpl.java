package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repository.BidRepository;
import com.nnk.springboot.service.interfaces.BidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

	public Bid findById(Integer id) throws IllegalArgumentException {

		Optional<Bid> optionalBL = bidRepository.findById(id);

		if (optionalBL.isEmpty()) {
			log.error("bid with id " + id + " not found");
			throw new IllegalArgumentException("Invalid bid Id: " + id);
		}
		return optionalBL.get();
	}

	public void delete(Bid bid) {
		Optional<Bid> optionalBL = bidRepository.findById(bid.getId());
		if (optionalBL.isEmpty()) {
			log.error("bid with id " + bid.getId() + " not found");
			throw new IllegalArgumentException("Invalid bid Id: " + bid.getId());
		}
		bidRepository.delete(bid);
	}
}
