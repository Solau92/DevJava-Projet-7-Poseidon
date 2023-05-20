package com.nnk.springboot.configtests;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User with username " + username + " not found");
		}

		List<GrantedAuthority> listAutho = new ArrayList<>();
		listAutho.add(new SimpleGrantedAuthority(user.getRole()));

		System.out.println("Role dans loadUser... : " + user.getRole());
		System.out.println("Liste autho : " + listAutho);

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				listAutho
		);
	}

}
