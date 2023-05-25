package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BidRepository extends JpaRepository<Bid, Integer>, JpaSpecificationExecutor<Bid> {
}
