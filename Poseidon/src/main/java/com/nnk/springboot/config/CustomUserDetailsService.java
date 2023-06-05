package com.nnk.springboot.config;

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

/**
 * Implements UserDetailsService.
 * Loads user-specific data.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Locates the user based on the username.
	 *
	 * @param username identifiying the user whose data is required
	 * @return a fully populated user record
	 * @throws UsernameNotFoundException
	 */
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

}
