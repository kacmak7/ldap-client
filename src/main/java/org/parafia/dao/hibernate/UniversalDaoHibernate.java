package org.parafia.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.parafia.dao.UniversalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * This class serves as the a class that can CRUD any object witout any
 * Spring configuration. The only downside is it does require casting
 * from Object to the object class.
 *
 * @author Bryan Noll
 */
@Repository("universalDao")
public class UniversalDaoHibernate extends HibernateDaoSupport implements UniversalDao {
    @Autowired
	public UniversalDaoHibernate(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	/**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    public Object save(Object o) {
        return getHibernateTemplate().merge(o);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
	public Object get(Class clazz, Serializable id) {
        @SuppressWarnings("unchecked")
		Object o = getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAll(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public void remove( Class clazz, Serializable id) {
        getHibernateTemplate().delete(get(clazz, id));
    }

	@Override
	public void evict(Object o)
	{
		getHibernateTemplate().evict(o);
	}
	
    public void flush()
    {
    	getHibernateTemplate().flush();;
    }
}
