package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "bid")
@Data
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotBlank(message = "Account is mandatory")
	private String account;

	@NotBlank(message = "Type is mandatory")
	private String type;

	private Double bidQuantity;

	private Double askQuantity;

	private Double bid;

	private Double ask;

	private String benchmark;

	private LocalDate bidDate;

	private String commentary;

	private String security;

	private String status;

	private String trader;

	private String book;

	private String creationName;

	private LocalDate creationDate;

	private String revisionName;

	private LocalDate revisionDate;

	private String dealName;

	private String dealTYpe;

	private String sourceListId;

	private String side;




}
