package org.parafia.dao;

import java.sql.Date;
import java.util.List;

import org.parafia.model.IntentionAnnotation;

public interface IntentionAnnotationDao extends GenericDao<IntentionAnnotation, Long> {
	
	List<IntentionAnnotation> loadIntentionAnnotations(Date startDate, Date endDate);
	
	void delete(IntentionAnnotation intentionAnnotation);
}
