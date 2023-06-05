package com.nnk.springboot.service.implementations;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Returns all the users in database.
	 *
	 * @return a List<User>
	 */
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Encodes password and saves the given user in database.
	 *
	 * @param user
	 * @return the user saved
	 */
	@Override
	public User save(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/**
	 * Searches in database a user given an id.
	 *
	 * @param id
	 * @return the user if found
	 * @throws IllegalArgumentException if the user was not found
	 */
	@Override
	public User findById(Integer id) {

		Optional<User> optionalU = userRepository.findById(id);

		if (optionalU.isEmpty()) {
			log.error("user with id " + id + " not found");
			throw new IllegalArgumentException("Invalid user Id: " + id);
		}
		return optionalU.get();
	}

	/**
	 * Deletes in database the given user.
	 *
	 * @param user
	 * @throws IllegalArgumentException if the user was not found
	 */
	@Override
	public void delete(User user) {

		Optional<User> optionalU = userRepository.findById(user.getId());
		if (optionalU.isEmpty()) {
			log.error("user with id " + user.getId() + " not found");
			throw new IllegalArgumentException("Invalid user Id: " + user.getId());
		}
		userRepository.delete(user);
	}

	/**
	 * Returns the current logged user.
	 *
	 * @return User
	 */
	@Override
	public User getLoggedUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
			log.info("Regular user");
			return userRepository.findByUsername(authentication == null ? "" : authentication.getName());
		} else {
			log.info("GitHub user");
			User user = new User();
			user.setUsername(
					((OAuth2AuthenticationToken)
							SecurityContextHolder.getContext().getAuthentication()).getPrincipal().getAttribute("login"));
			return user;
		}
	}

	/**
	 * Searches in database a user given a username.
	 *
	 * @param username
	 * @return the user if found
	 */
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
