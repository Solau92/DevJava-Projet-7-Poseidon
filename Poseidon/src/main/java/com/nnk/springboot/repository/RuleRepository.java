package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RuleRepository extends JpaRepository<Rule, Integer>, JpaSpecificationExecutor<Rule> {
}
