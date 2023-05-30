package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repository.RuleRepository;
import com.nnk.springboot.service.interfaces.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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
			log.error("rule with id " + id + " not found");
			throw new IllegalArgumentException("Invalid rule Id: " + id);
		}
		return optionalR.get();
	}

	@Override
	public void delete(Rule rule) {

		Optional<Rule> optionalBL = ruleRepository.findById(rule.getId());
		if(optionalBL.isEmpty()) {
			log.error("rule with id " + rule.getId() + " not found");
			throw new IllegalArgumentException("Invalid rul Id: " + rule.getId());
		}
		ruleRepository.delete(rule);
	}
}
