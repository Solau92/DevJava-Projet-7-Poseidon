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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
		return new MySimpleUrlAuthenticationSuccessHandler();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(authorize ->
						{
							try {
								authorize
										.requestMatchers("/css/**").permitAll()
										.requestMatchers("/", "/oauth2login").permitAll()
										.requestMatchers("/user/**", "/admin/**").hasAuthority("ADMIN")
										.requestMatchers("/bid/**", "/curvePoint/**", "/rating/**", "/trade/**", "/rule/**").authenticated()
										.requestMatchers("/app-logout", "/access-denied").permitAll()
										.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}

				)
				.formLogin(
						form -> form
								.successHandler(myAuthenticationSuccessHandler())
								.permitAll()
				)
				.oauth2Login(
						o -> o
								.defaultSuccessUrl("/oauth2login", true)
				).logout(
						logout -> logout
								.logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"))
								.logoutSuccessUrl("/login")
				);

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

}
