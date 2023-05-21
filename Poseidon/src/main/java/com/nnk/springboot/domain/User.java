package com.nnk.springboot.domain;

import com.nnk.springboot.util.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Password is mandatory")
	@ValidPassword
//	@Size(min = 4, message = "Password must contain at least 4 characters") // 8
	private String password;

	@NotBlank(message = "FullName is mandatory")
	private String fullname;

	@NotBlank(message = "Role is mandatory")
	private String role;

}
