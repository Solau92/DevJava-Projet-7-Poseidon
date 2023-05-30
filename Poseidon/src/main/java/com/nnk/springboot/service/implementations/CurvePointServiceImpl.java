package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.interfaces.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CurvePointServiceImpl implements CurvePointService {

	private CurvePointRepository curvePointRepository;

	public CurvePointServiceImpl(CurvePointRepository curvePointRepository){
		this.curvePointRepository = curvePointRepository;
	}

	@Override
	public List<CurvePoint> findAll() {
		return curvePointRepository.findAll();
	}

	@Override
	public CurvePoint save(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public CurvePoint findById(Integer id) {

		Optional<CurvePoint> optionalCP = curvePointRepository.findById(id);

		if(optionalCP.isEmpty()) {
			log.error("curvePoint with id " + id + " not found");
			throw new IllegalArgumentException("Invalid curvePoint Id: " + id);
		}
		return optionalCP.get();
	}

	@Override
	public void delete(CurvePoint curvePoint) {

		Optional<CurvePoint> optionalCP = curvePointRepository.findById(curvePoint.getId());
		if(optionalCP.isEmpty()) {
			log.error("curvePoint with id " + curvePoint.getId() + " not found");
			throw new IllegalArgumentException("Invalid curvePoint Id: " + curvePoint.getId());
		}
		curvePointRepository.delete(curvePoint);
	}

}
