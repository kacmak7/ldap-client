package org.parafia.service;

import java.util.Date;
import java.util.List;

import org.parafia.model.Celebrant;
import org.parafia.model.Intention;
import org.parafia.model.IntentionType;
import org.parafia.model.IntentionTypeDuration;

public interface IntentionManager extends GenericManager<Intention, Long>{
	// List<Intention> getAll(Date dateFrom, Date dateTo);

	List<Celebrant> loadActiveCelebrants();

	List<Celebrant> loadActiveConfessions();

	List<Celebrant> loadAllCelebrants();

	List<Celebrant> loadAllConfessions();

	List<IntentionType> loadAllTypes();

	List<IntentionTypeDuration> loadAllTypeDuration();

	List<Intention> loadIntentions(Date startDate, Date endDate);

	List<Intention> loadIntentions(Date date);

	Intention save(Intention intention);

	void delete(long intentionId);

	Intention get(Long id);
}
