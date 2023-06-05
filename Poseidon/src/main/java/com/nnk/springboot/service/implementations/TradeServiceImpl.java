package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repository.TradeRepository;
import com.nnk.springboot.service.interfaces.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TradeServiceImpl implements TradeService {

	private TradeRepository tradeRepository;

	public TradeServiceImpl(TradeRepository tradeRepository){
		this.tradeRepository = tradeRepository;
	}

	/**
	 * Returns all the trades in database.
	 *
	 * @return a List<Trade>
	 */
	@Override
	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}

	/**
	 * Saves the given trade in database.
	 *
	 * @param trade
	 * @return the trade saved
	 */
	@Override
	public Trade save(Trade trade) {
		return tradeRepository.save(trade);
	}

	/**
	 * Searches in database a trade given an id.
	 *
	 * @param id
	 * @return the trade if found
	 * @throws IllegalArgumentException if the trade was not found
	 */
	@Override
	public Trade findById(Integer id) {
		Optional<Trade> optionalT = tradeRepository.findById(id);

		if(optionalT.isEmpty()) {
			log.error("trade with id " + id + " not found");
			throw new IllegalArgumentException("Invalid trade Id: " + id);
		}
		return optionalT.get();	}

	/**
	 * Deletes in database the given trade.
	 *
	 * @param trade
	 * @throws IllegalArgumentException if the trade was not found
	 */
	@Override
	public void delete(Trade trade) {
		Optional<Trade> optionalT = tradeRepository.findById(trade.getId());
		if(optionalT.isEmpty()) {
			log.error("trade with id " + trade.getId() + " not found");
			throw new IllegalArgumentException("Invalid trade Id: " + trade.getId());
		}
		tradeRepository.delete(trade);
	}
}
