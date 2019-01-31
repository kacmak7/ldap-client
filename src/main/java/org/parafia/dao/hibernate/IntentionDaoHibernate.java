package org.parafia.dao.hibernate;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.parafia.dao.IntentionDao;
import org.parafia.model.Celebrant;
import org.parafia.model.Intention;
import org.parafia.model.IntentionType;
import org.parafia.model.IntentionTypeDuration;
import org.springframework.stereotype.Repository;

@Repository("intentionDao")
public class IntentionDaoHibernate extends GenericDaoHibernate<Intention, Long> implements IntentionDao {
	public IntentionDaoHibernate() {
		super(Intention.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Celebrant> loadAllCelebrants() {
		//return getHibernateTemplate().loadAll(Celebrant.class);
		return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Celebrant.class).addOrder(Order.asc("surname")));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Celebrant> loadAllConfessions() {
		//return getHibernateTemplate().loadAll(Celebrant.class);
		return getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Celebrant.class).addOrder(Order.asc("surname")));
	}
	
	@Override
	public List<IntentionType> loadAllTypes() {
		return getHibernateTemplate().loadAll(IntentionType.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IntentionTypeDuration> loadAllTypeDuration() {
		return getHibernateTemplate().loadAll(IntentionTypeDuration.class);
	}
	
	@Override
	public List<Celebrant> loadActiveCelebrants() {
		//return getHibernateTemplate().loadAll(Celebrant.class);
		return getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Celebrant.class).
				add(Restrictions.eq("active", true)).
				addOrder(Order.asc("surname"))
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Celebrant> loadActiveConfessions() {
		//return getHibernateTemplate().loadAll(Celebrant.class);
		return getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Celebrant.class).
				add(Restrictions.eq("active", true)).
				addOrder(Order.asc("surname"))
		);
	}	
	
	public List<Intention> loadIntentions(Date startDate, Date endDate) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Intention.class);
		
		if (startDate != null)
			criteria.add(Restrictions.ge("date", startDate));
		if (endDate != null)
			criteria.add(Restrictions.le("date", endDate));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Intention> intentions = getHibernateTemplate().findByCriteria(criteria);
		
		return intentions;
	}
	
	public void delete(Intention intention) {
		getHibernateTemplate().delete(intention);
	}
}
