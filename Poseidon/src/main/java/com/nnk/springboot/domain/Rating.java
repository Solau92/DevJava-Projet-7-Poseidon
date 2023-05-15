package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "rating")
@Data
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Moody's rating is mandatory")
	private String moodysRating;

	@NotBlank(message = "S and P's rating is mandatory")
	private String sAndPRating;

	@NotBlank(message = "Fitch's rating is mandatory")
	private String fitchRating;

	private Integer orderNumber;


}
