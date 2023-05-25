package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TradeRepository extends JpaRepository<Trade, Integer>, JpaSpecificationExecutor<Trade> {
}
