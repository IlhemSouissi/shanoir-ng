package org.shanoir.ng.service;

import java.util.List;

import org.shanoir.ng.model.User;
import org.shanoir.ng.model.exception.ShanoirUsersException;
import org.shanoir.ng.model.validation.UniqueCheckableService;

/**
 * User service.
 *
 * @author msimon
 * @author jlouis
 *
 */
public interface UserService extends UniqueCheckableService<User>{

	/**
	 * Delete a user
	 * @param id
	 */
	void deleteById(Long id);
	
    /**
     * Get all the users
     * @return a list of users
     */
    List<User> findAll();

    /**
     * Find user by its email address
     *
     * @param email
     * @return a user or null
     */
    User findByEmail(String email);

    /**
     * Find user by its id
     *
     * @param id
     * @return a user or null
     */
    User findById(Long id);

    /**
     * Find user by its username
     *
     * @param id
     * @return a user or null
     */
    User findByUsername(String username);

    /**
     * Save a user.
     *
     * @param user user to create.
     * @return created user.
     */
    User save(User user);

    /**
     * Update a user from the old Shanoir
     * @param user
     * @throws ShanoirUsersException
     */
    void updateFromShanoirOld(User user) throws ShanoirUsersException;

    /**
     * Update a user.
     *
     * @param user user to update.
     * @return updated user.
     * @throws ShanoirUsersException
     */
    User update(User user) throws ShanoirUsersException;

}
