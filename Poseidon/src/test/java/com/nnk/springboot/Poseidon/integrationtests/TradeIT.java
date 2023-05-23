package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeIT {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = new Trade();
		trade.setAccount("account");
		trade.setType("type");

		// Save
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getId());
		Assert.assertTrue(trade.getAccount().equals("account"));

		// Update
		trade.setAccount("account updated");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("account updated"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
