package org.parafia.service.impl;

import org.parafia.dao.GenericDao;
import org.parafia.model.Celebrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("celebrantManager")
public class CelebrantManager extends GenericManagerImpl<Celebrant, Long>
{
	@Autowired
	public CelebrantManager(@Qualifier("celebrantDao") GenericDao<Celebrant, Long> dao) {
		super.genericDao = dao;
	}
}
