/*

package com.nnk.springboot.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mysql://localhost:3306/demo");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("mysql!");
		return dataSourceBuilder.build();
	}

	@Bean
	public UserDetailsManager userDetailsService() {

		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource());
		users.createUser(user);
		return users;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(authorize ->
						authorize
								.requestMatchers("/", "/home").permitAll()
								.requestMatchers("/user").hasRole("ADMIN")
								.anyRequest().authenticated()
						)
				.formLogin(
//						form -> form
//								.loginPage("/login")
//								.loginProcessingUrl("/login")
//								.defaultSuccessUrl("/bid/list")
//								.permitAll()
//				)
//				.logout(
//						logout -> logout.permitAll()
//								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//								.logoutSuccessUrl("/")
				);

		return http.build();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}





}

*/
