/*
package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize ->
						authorize
								.requestMatchers("/", "/home").permitAll()
								.requestMatchers("/user").hasRole("ADMIN")
								.anyRequest().authenticated()
				).formLogin(
						//						form -> form
						//								.loginPage("/login")
						//								.loginProcessingUrl("/login")
						//								.defaultSuccessUrl("/user/home")
						//								.permitAll()
						//				).logout(
						//						logout -> logout
						//								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						//								.logoutSuccessUrl("/logoff")
				);

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

}
*/
