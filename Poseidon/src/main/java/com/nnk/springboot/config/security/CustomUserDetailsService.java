package com.nnk.springboot.config.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repository.UserRepository;
import com.nnk.springboot.service.implementations.UserServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	private UserServiceImpl userService;

	public CustomUserDetailsService(UserRepository userRepository, UserServiceImpl userService) {

		this.userRepository = userRepository;
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User with username " + username + " not found");
		}

		List<GrantedAuthority> listAutho = new ArrayList<>();
		listAutho.add(new SimpleGrantedAuthority(user.getRole()));

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				listAutho
		);
	}

//	public boolean hasRole(String role) {
//
//		User user = userService.getLoggedUser();
//
//		return user.getRole().equals(role);
//	}

}
