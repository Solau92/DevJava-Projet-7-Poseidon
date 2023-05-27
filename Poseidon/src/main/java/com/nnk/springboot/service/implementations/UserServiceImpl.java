package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.interfaces.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

	@Override
	public User getLoggedUser() {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		User user = new User();
//		user.setUsername("Oauth2GitHub");
//
//
////		if (authentication.getAuthorities().contains("OAUTH2_USER")) {
////			user.setUsername("Oauth2GitHub");
////			return user;
////		}
//
////		User user = (User) authentication.getPrincipal();
//
////		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		//		.getPrincipal() avec Oauth2
//
//		if (userRepository.findByUsername(authentication.getName()) == null) {
//			return user;
//		} else {
//			return userRepository.findByUsername(authentication.getName());
//		}

//		return userRepository.findByUsername(authentication == null ? "" : authentication.getName());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
			return userRepository.findByUsername(authentication == null ? "" : authentication.getName());
		}
		else {
			User user = new User();
			user.setUsername(
					((OAuth2AuthenticationToken)
							SecurityContextHolder.getContext().getAuthentication()).getPrincipal().getAttribute("login"));
			return user;
		}
	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}

}
