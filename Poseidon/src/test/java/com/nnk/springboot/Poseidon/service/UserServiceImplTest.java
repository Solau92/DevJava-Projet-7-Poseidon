package com.nnk.springboot.Poseidon.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	private User user1 = new User();

	private User user2 = new User();

	private List<User> userList = new ArrayList<>();

	private Optional<User> optionalUser;

	private Optional<User> emptyOptional;


	@BeforeEach
	void setUp(){

		user1.setId(1);
		user1.setUsername("username1");
		user1.setFullname("fullname1");
		user1.setPassword("password1");

		user1.setId(2);
		user1.setUsername("username2");
		user1.setFullname("fullname2");

		userList.add(user1);
		userList.add(user2);

		optionalUser = Optional.of(user1);

		emptyOptional = Optional.empty();
	}

	@Test
	void findAll_Ok_Test(){

		// GIVEN
		when(userRepository.findAll()).thenReturn(userList);

		// WHEN
		List<User> result = userService.findAll();

		// THEN
		assertEquals(user1, result.get(0));
		assertEquals(user2, result.get(1));
	}

	@Test
	void save_Ok_Test(){

		// GIVEN
		when(userRepository.save(any(User.class))).thenReturn(user1);

		// WHEN
		User expected = userService.save(user1);

		// THEN
		assertEquals(user1, expected);
	}

	@Test
	void findById_Ok_Test(){

		// GIVEN
		when(userRepository.findById(anyInt())).thenReturn(optionalUser);

		// WHEN
		User expected = userService.findById(1);

		// THEN
		assertEquals(user1, expected);
	}

	@Test
	void findById_Error_Test(){

		// GIVEN
		when(userRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> userService.findById(5));
	}

	@Test
	void delete_Ok_Test(){

		// GIVEN
		when(userRepository.findById(anyInt())).thenReturn(optionalUser);

		// WHEN
		userService.delete(user1);

		// THEN
		verify(userRepository, Mockito.times(1)).delete(user1);
	}

	@Test
	void delete_Error_Test(){

		// GIVEN
		when(userRepository.findById(anyInt())).thenReturn(emptyOptional);

		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> userService.delete(user1));

	}
}
