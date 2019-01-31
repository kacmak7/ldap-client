package org.parafia.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parafia.dao.UniversalDao;
import org.parafia.service.UniversalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("manager")
public class UniversalManagerImpl implements UniversalManager {
    /**
     * Log instance for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * UniversalDao instance, ready to charge forward and persist to the database
     */
    @Autowired
    @Qualifier("universalDao")
    protected UniversalDao dao;

    /**
     * {@inheritDoc}
     */
    public Object get(Class clazz, Serializable id) {
        return dao.get(clazz, id);
    }

    /**
     * {@inheritDoc}
     */
    public List getAll(Class clazz) {
        return dao.getAll(clazz);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(Class clazz, Serializable id) {
        dao.remove(clazz, id);
    }

    /**
     * {@inheritDoc}
     */
    public Object save(Object o) {
        return dao.save(o);
    }

	@Override
	public void detach(Object o)
	{
		dao.evict(o);
	}
	
    public void flush()
    {
    	dao.flush();;
    }
}
