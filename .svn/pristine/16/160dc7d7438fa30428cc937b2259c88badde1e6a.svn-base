package org.parafia.service.impl;

import java.util.Date;
import java.util.List;

import org.parafia.dao.IntentionAnnotationDao;
import org.parafia.model.IntentionAnnotation;
import org.parafia.service.IntentionAnnotationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("intentionAnnotationManager")
public class IntentionAnnotationManagerImpl extends GenericManagerImpl<IntentionAnnotation, Long> implements IntentionAnnotationManager {
	//@Autowired
	private IntentionAnnotationDao dao;		//TODO: it's already the second dao
	
	@Autowired
	public IntentionAnnotationManagerImpl(IntentionAnnotationDao genericDao) {
		
		super(genericDao);
		this.dao = genericDao;
		// TODO This constructor is useless, need to remove it when all managers are Autowired
	}

	@Override
	public List<IntentionAnnotation> loadIntentionAnnotations(Date startDate, Date endDate) {
		return dao.loadIntentionAnnotations(new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
	}
	
	@Override
	public List<IntentionAnnotation> loadIntentionAnnotations(Date date) {
		//Date d = adjustDate(date);
		Date d = date;
		
		return dao.loadIntentionAnnotations(new java.sql.Date(d.getTime()), new java.sql.Date(d.getTime()));
	}
	
	@Override
	public IntentionAnnotation save(IntentionAnnotation annotation) {
		if (annotation.getId() == null) {
			//decrease date for new intentions
			
			//intention.setDate(adjustDate(intention.getDate()));
		}
		
		return dao.save(annotation);
	}
	
	@Override
	public void delete(long intentionAnnotationId) {
		dao.delete(dao.get(intentionAnnotationId));
	}
}
