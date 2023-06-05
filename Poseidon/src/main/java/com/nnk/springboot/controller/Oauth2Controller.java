package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.implementations.UserServiceImpl;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Controller that manage Oauth2 login
 */
@Slf4j
@Controller
public class Oauth2Controller {

	UserServiceImpl userService;

	public Oauth2Controller(UserServiceImpl userService) {
		this.userService = userService;
	}

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/oauth2login")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {

		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(
						authentication.getAuthorizedClientRegistrationId(),
						authentication.getName());

		String userInfoEndpointUri = client.getClientRegistration()
				.getProviderDetails().getUserInfoEndpoint().getUri();

		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
					.getTokenValue());
			HttpEntity entity = new HttpEntity("", headers);
			ResponseEntity<Map> response = restTemplate
					.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
			Map userAttributes = response.getBody();

			String userName = userAttributes.get("login").toString();

			if(userService.findByUsername(userName) == null){
				log.info("user with username " + userName + " not found");

				User user = new User();
				user.setUsername(userAttributes.get("login").toString());
				user.setRole("USER");
				user.setFullname(userAttributes.get("login").toString() + " (githubUser)");
				user.setPassword("123456A*");

				userService.save(user);
				log.info("user with username " + userName + " saved");
			}

		}
		return "redirect:/bid/list";
	}

}
