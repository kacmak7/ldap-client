package org.parafia.dao;

import java.sql.Date;
import java.util.List;

import org.parafia.model.Celebrant;
import org.parafia.model.Intention;
import org.parafia.model.IntentionType;
import org.parafia.model.IntentionTypeDuration;

public interface IntentionDao extends GenericDao<Intention, Long> {
	List<Celebrant> loadActiveCelebrants();
	
	List<Celebrant> loadAllCelebrants();
	
	List<Celebrant> loadActiveConfessions();
	
	List<Celebrant> loadAllConfessions();
	
	List<IntentionType> loadAllTypes();
	
	List<IntentionTypeDuration> loadAllTypeDuration();
	
	List<Intention> loadIntentions(Date startDate, Date endDate);
	
	void delete(Intention intention);
}
