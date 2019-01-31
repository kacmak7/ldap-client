package org.parafia.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parafia.dao.GenericDao;
import org.parafia.service.GenericManager;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should always extend.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public abstract class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK>
{
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from
	 * Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * GenericDao instance, set by constructor of this class
	 */
	protected GenericDao<T, PK> genericDao;

	/**
	 * Public constructor for creating a new GenericManagerImpl.
	 * 
	 * @param genericDao
	 *            the GenericDao to use for persistence
	 */
	public GenericManagerImpl(final GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	public GenericManagerImpl() {
		// this.genericDao = genericDao;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll()
	{
		return genericDao.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(PK id)
	{
		return genericDao.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(PK id)
	{
		return genericDao.exists(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public T save(T object)
	{
		return genericDao.save(object);
	}

	public void detach(Object obj)
	{
		genericDao.evict(obj);
	}
	
	public void premergeSave(Object obj)
	{
		genericDao.premergeSave(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id)
	{
		genericDao.remove(id);
	}
	
    public void flush()
    {
    	genericDao.flush();;
    }
}
