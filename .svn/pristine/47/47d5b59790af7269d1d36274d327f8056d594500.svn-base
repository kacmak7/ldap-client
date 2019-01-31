package org.parafia.service;

import org.parafia.model.Role;

import java.util.List;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface RoleManager extends UniversalManager {
    /**
     * {@inheritDoc}
     */
    List getRoles();

    /**
     * {@inheritDoc}
     */
    Role getRole(String rolename);
    
    Role getRole(Long id);

    /**
     * {@inheritDoc}
     */
    Role saveRole(Role role);

    /**
     * {@inheritDoc}
     */
    void removeRole(String rolename);
}
