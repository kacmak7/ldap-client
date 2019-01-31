package org.parafia.service.impl;

import java.util.List;

import org.parafia.dao.RoleDao;
import org.parafia.model.Role;
import org.parafia.service.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of RoleManager interface.
 * 
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("roleManager")
public class RoleManagerImpl extends UniversalManagerImpl implements RoleManager {
    @Autowired
	private RoleDao dao;

	/**
     * {@inheritDoc}
     */
    public List<Role> getRoles() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
        return dao.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public Role saveRole(Role role) {
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        dao.removeRole(rolename);
    }

	@Override
	public Role getRole(Long id)
	{
		return dao.getRoleById(id);
	}
}