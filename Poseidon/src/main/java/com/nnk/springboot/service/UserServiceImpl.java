package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.interfaces.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User findById(Integer id) {
		Optional<User> optionalU = userRepository.findById(id);

		if(optionalU.isEmpty()) {
			throw new IllegalArgumentException("Invalid user Id: " + id);
		}
		return optionalU.get();
	}

	@Override
	public void delete(User user) {

		Optional<User> optionalU = userRepository.findById(user.getId());
		if (optionalU.isEmpty()) {
			throw new IllegalArgumentException("Invalid user Id: " + user.getId());
		}
		userRepository.delete(user);
	}

//	@Override
	public User getLoggedUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByUsername(authentication == null ? "" : authentication.getName());
	}

//	@Override
//	public String getDefaultUrl() {
//
//		if(getLoggedUser().getRole().equals("ADMIN")) {
//			return "/user/list";
//		}
//		return "/bid/list";
//	}
}
