package org.parafia.service;

import java.util.Date;
import java.util.List;

import org.parafia.model.IntentionAnnotation;

public interface IntentionAnnotationManager extends GenericManager<IntentionAnnotation, Long> {
	
	List<IntentionAnnotation> loadIntentionAnnotations(Date startDate, Date endDate);
	
	List<IntentionAnnotation> loadIntentionAnnotations(Date date);
	
	void delete(long intentionAnnotationId);
}
