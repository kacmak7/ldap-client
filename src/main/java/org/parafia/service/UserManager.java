package org.parafia.service;

import java.util.List;

import org.parafia.dao.UserDao;
import org.parafia.model.User;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface UserManager extends UniversalManager {

    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    //void setUserDao(UserDao userDao);

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    User getUser(String userId);
    
    User loadUserByUsername(final String username) throws UsernameNotFoundException;

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     * @param user parameters to filter on
     * @return List
     */
    List getUsers(User user);

    /**
     * Saves a user's information.
     *
     * @param user the user's information
     * @throws ObjectExistsException thrown when user already exists
     * @return user the updated user object
     */
    User saveUser(User user) throws ObjectExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    void removeUser(String userId);
}
