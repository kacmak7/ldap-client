package org.parafia.service.impl;

import org.parafia.dao.UserDao;
import org.parafia.model.User;
import org.parafia.service.UserManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.persistence.EntityExistsException;
import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 */
//@WebService(serviceName = "userManager", endpointInterface = "org.parafia.service.UserManager")
@Service("userManager")
public class UserManagerImpl extends UniversalManagerImpl implements UserManager, UserDetailsService {
    
	@Autowired(required=true)
	private UserDao dao;
    
	@Autowired(required=true)
	private DaoAuthenticationProvider authenticationProvider;
	
	@Autowired(required=true)
	private PasswordEncoder passwordEncoder;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao the UserDao that communicates with the database
     */
    /*@Required
    public void setUserDao(UserDao dao) {
    	log.error("setting the UserDao " + dao.toString());
        this.dao = dao;
    }*/

    /**
     * Set the DaoAuthenticationProvider object that will provide both the
     * PasswordEncoder and the SaltSource which will be used for password
     * encryption when necessary.
     * @param authenticationProvider the DaoAuthenticationProvider object
     */
    /*@Required
    public void setAuthenticationProvider(DaoAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }*/

    /**
     * {@inheritDoc}
     */
    public User getUser(String userId) {
        return dao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers(User user) {
        return dao.getUsers();
    }
    
    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws ObjectExistsException {
        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }
        
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (authenticationProvider != null) {
            //PasswordEncoder passwordEncoder = authenticationProvider.getPasswordEncoder();

            if (passwordEncoder != null) {
            	user.setDeleteCode(passwordEncoder.encodePassword(user.getDeleteCode(), null));
            	
                // Check whether we have to encrypt (or re-encrypt) the password
                if (user.getVersion() == null) {
                    // New user, always encrypt
                    passwordChanged = true;
                } else {
                    // Existing user, check password in DB
                    String currentPassword = dao.getUserPassword(user.getUsername());
                    if (currentPassword == null) {
                        passwordChanged = true;
                    } else {
                        if (!currentPassword.equals(user.getPassword())) {
                            passwordChanged = true;
                        }
                    }
                }

                // If password was changed (or new user), encrypt it
                if (passwordChanged) {
                    user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
                }
            } else {
                log.warn("PasswordEncoder not set, skipping password encryption...");
            }
        } else {
            log.warn("AuthenticationProvider not set, skipping password encryption...");

        }
        
        try {
            return dao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
        log.debug("removing user: " + userId);
        dao.remove(new Long(userId));
    }

    @Transactional(readOnly=true)
	@Override
	public User loadUserByUsername(final String username)
		throws UsernameNotFoundException {

		User user = dao.loadUserByUsername(username);
		return user;
		/*List<GrantedAuthority> authorities =
                                      buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);*/

	}
}
