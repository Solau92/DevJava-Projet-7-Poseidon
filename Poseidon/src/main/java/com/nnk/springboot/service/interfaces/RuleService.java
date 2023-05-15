package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Rule;

import java.util.List;

public interface RuleService {
	List<Rule> findAll();

	Rule save(Rule rule);

	Rule findById(Integer id);

	void delete(Rule rule);
}
