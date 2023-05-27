package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "curvepoint")
@Data
public class CurvePoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Positive(message = "Must be a positive number")
	private Integer curveId;

	private LocalDate asOfDate;

	private Double term;

	private Double value;

	private LocalDate creationDate;

}
