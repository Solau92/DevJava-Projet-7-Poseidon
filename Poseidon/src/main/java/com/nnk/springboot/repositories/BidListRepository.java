package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BidListRepository extends JpaRepository<BidList, Integer>, JpaSpecificationExecutor<BidList> {
}
