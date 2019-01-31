package org.parafia.dao.hibernate;

import java.util.List;

import org.parafia.dao.RoleDao;
import org.parafia.model.Role;
import org.springframework.stereotype.Repository;


/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> 
 */
@Repository("roleDao")
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {

	/**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
		super(Role.class);
	}

	
	

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public Role getRoleByName(String rolename) {
        List roles = getHibernateTemplate().find("from Role where name=?", rolename);
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getHibernateTemplate().delete(role);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public Role getRoleById(Long id)
	{
		List roles = getHibernateTemplate().find("from Role where id=?", id);
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
}
	}
}
