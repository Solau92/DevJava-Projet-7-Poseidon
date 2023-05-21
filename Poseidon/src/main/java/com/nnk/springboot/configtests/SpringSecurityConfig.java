package com.nnk.springboot.configtests;

import com.nnk.springboot.service.UserServiceImpl;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	//	@Autowired
	//	private LoginSuccessHandler loginSuccessHandler;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(authorize ->
						authorize
								.requestMatchers("/css/**").permitAll()
								.requestMatchers("/").permitAll()
								.requestMatchers("/user/**").hasAuthority("ADMIN")
								.requestMatchers("/bid/**", "/curvePoint/**", "/rating/**", "/trade/**", "/rule/**").authenticated()
				)
				.formLogin(
						form -> form
								//								.loginPage("/login")
								////								.loginProcessingUrl("/login")
								//								.successHandler(loginSuccessHandler)
								.defaultSuccessUrl("/bid/list")
								.permitAll()
				)
				.oauth2Login(
						o -> o
								.defaultSuccessUrl("/bid/list")

//				).logout(
//						logout -> logout
//								.logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"))
//								.logoutSuccessUrl("/login")
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
