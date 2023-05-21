package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {

	List<User> findAll();

	User save(User user);

	User findById(Integer id);

	void delete(User user);

//	User getLoggedUser();
//
//	String getDefaultUrl();
}
