package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Rule;

import java.util.List;

public interface RuleService {

	/**
	 * Returns all the rules.
	 *
	 * @return a List<Rule>
	 */
	List<Rule> findAll();

	/**
	 * Saves the given rule point.
	 *
	 * @param rule
	 * @return the rule saved
	 */
	Rule save(Rule rule);

	/**
	 * Searches a rule given an id.
	 *
	 * @param id
	 * @return the rule if found
	 * @throws IllegalArgumentException if the rule was not found
	 */
	Rule findById(Integer id);

	/**
	 * Deletes the given curve rule.
	 *
	 * @param rule
	 * @throws IllegalArgumentException if the rule was not found
	 */
	void delete(Rule rule);
}
