package com.nnk.springboot.service.implementation;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.service.interfaces.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

	private RuleRepository ruleRepository;

	public RuleServiceImpl(RuleRepository ruleRepository){
		this.ruleRepository = ruleRepository;
	}

	@Override
	public List<Rule> findAll() {
		return ruleRepository.findAll();
	}

	@Override
	public Rule save(Rule rule) {
		return ruleRepository.save(rule);
	}

	@Override
	public Rule findById(Integer id) {
		Optional<Rule> optionalR = ruleRepository.findById(id);

		if(optionalR.isEmpty()) {
			throw new IllegalArgumentException("Invalid rule Id: " + id);
		}
		return optionalR.get();
	}

	@Override
	public void delete(Rule rule) {
		Optional<Rule> optionalBL = ruleRepository.findById(rule.getId());
		if(optionalBL.isEmpty()) {
			throw new IllegalArgumentException("Invalid rul Id: " + rule.getId());
		}
		ruleRepository.delete(rule);
	}
}
