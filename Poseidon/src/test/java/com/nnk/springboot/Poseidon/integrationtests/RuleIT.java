package com.nnk.springboot.Poseidon.integrationtests;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repository.RuleRepository;
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
public class RuleIT {

	@Autowired
	private RuleRepository ruleRepository;

	@Test
	public void ruleTest() {
		Rule rule = new Rule();
		rule.setName("name");

		// Save
		rule = ruleRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("name"));

		// Update
		rule.setName("name updated");
		rule = ruleRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("name updated"));

		// Find
		List<Rule> listResult = ruleRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleRepository.delete(rule);
		Optional<Rule> ruleList = ruleRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
