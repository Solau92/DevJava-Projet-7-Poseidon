package com.nnk.springboot.repository;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer>, JpaSpecificationExecutor<CurvePoint> {

}
