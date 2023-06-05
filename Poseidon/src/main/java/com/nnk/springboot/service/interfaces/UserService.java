package com.nnk.springboot.service.interfaces;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {

	/**
	 * Returns all the users.
	 *
	 * @return a List<User>
	 */
	List<User> findAll();

	/**
	 * 	Saves the given user in database.
	 *
	 * @param user
	 * @return the user saved
	 */
	User save(User user);

	/**
	 * Searches a user given an id.
	 *
	 * @param id
	 * @return the user if found
	 */
	User findById(Integer id);

	/**
	 * Deletes the given user.
	 *
	 * @param user
	 */
	void delete(User user);

	/**
	 *
	 * @return
	 */
	User getLoggedUser();

	/**
	 * Searches a user given a username.
	 *
	 * @param username
	 * @return the user if found
	 */User findByUsername(String username);
}
