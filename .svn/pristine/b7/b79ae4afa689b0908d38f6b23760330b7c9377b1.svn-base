package org.parafia.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.parafia.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * This abstract class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should always extend
 * this class.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public abstract class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;

    
    @Autowired
	public void init(SessionFactory factory) {
	    setSessionFactory(factory);
	}
    
    /**
     * Constructor that takes in a class to see which type of entity to persist
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return super.getHibernateTemplate().loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return (T) super.getHibernateTemplate().merge(object);
    }

    public void evict(Object obj)
    {
    	super.getHibernateTemplate().evict(obj);
    }
    
    public void premergeSave(Object obj)
    {
    	super.getHibernateTemplate().save(obj);
    }
    
    public void flush()
    {
    	super.getHibernateTemplate().flush();;
    }
    
    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        super.getHibernateTemplate().delete(this.get(id));
    }
}
