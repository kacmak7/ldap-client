package org.parafia.dao.hibernate;

import java.sql.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.parafia.dao.IntentionAnnotationDao;
import org.parafia.model.IntentionAnnotation;
import org.springframework.stereotype.Repository;

@Repository("intentionAnnotationDao")
public class IntentionAnnotationDaoHibernate extends GenericDaoHibernate<IntentionAnnotation, Long> implements IntentionAnnotationDao {
	public IntentionAnnotationDaoHibernate() {
		super(IntentionAnnotation.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<IntentionAnnotation> loadIntentionAnnotations(Date startDate, Date endDate) {
		DetachedCriteria criteria = DetachedCriteria.forClass(IntentionAnnotation.class);
		if (startDate != null)
			criteria.add(Restrictions.ge("date", startDate));
		if (endDate != null)
			criteria.add(Restrictions.le("date", endDate));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public void delete(IntentionAnnotation intentionAnnotation) {
		getHibernateTemplate().delete(intentionAnnotation);
	}
}
