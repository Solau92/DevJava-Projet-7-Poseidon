//package com.nnk.springboot.config;
//
//import com.nnk.springboot.domain.User;
//import com.nnk.springboot.repositories.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//	private UserRepository userRepository;
//
//	public CustomUserDetailsService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//
//		User user = userRepository.findByUsername(userName);
//
//		if (user == null) {
//			throw new UsernameNotFoundException("User " + userName + " not found");
//
//		}
//
//		List<GrantedAuthority> grantedAutority = new ArrayList<>();
//		grantedAutority.add(new SimpleGrantedAuthority(user.getRole()));
//
//		return new org.springframework.security.core.userdetails.User(
//				user.getUsername(),
//				user.getPassword(),
//				grantedAutority
//		);
//	}
//
//}
