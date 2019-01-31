package org.parafia.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.parafia.dao.LookupDao;
import org.parafia.model.Role;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Repository;
/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Repository("lookupDao")
public class LookupDaoHibernate extends UniversalDaoHibernate implements LookupDao {

	@Autowired
	public LookupDaoHibernate(SessionFactory sessionFactory) {
		super(sessionFactory);
		this.setSessionFactory(sessionFactory);
	}
	
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return getHibernateTemplate().find("from Role order by name");
    }
}
