package org.parafia.dao.hibernate;

import org.parafia.model.Celebrant;
import org.springframework.stereotype.Repository;

@Repository("celebrantDao")
public class CelebrantDaoHibernate extends GenericDaoHibernate<Celebrant, Long>
{

	public CelebrantDaoHibernate() {
		super(Celebrant.class);
	}
}
