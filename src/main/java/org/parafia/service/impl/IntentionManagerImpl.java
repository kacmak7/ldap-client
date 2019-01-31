package org.parafia.service.impl;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.parafia.dao.GenericDao;
import org.parafia.dao.IntentionDao;
import org.parafia.model.Celebrant;
import org.parafia.model.Intention;
import org.parafia.model.IntentionType;
import org.parafia.model.IntentionTypeDuration;
import org.parafia.service.IntentionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("intentionManager")
public class IntentionManagerImpl extends GenericManagerImpl<Intention, Long> implements IntentionManager {
	
	@Autowired
	private IntentionDao dao;
	
	@Autowired
	public IntentionManagerImpl(@Qualifier("intentionDao") GenericDao<Intention, Long> dao)
	{
		super.genericDao = dao;
	}

	@Override
	public List<Celebrant> loadAllCelebrants() {
		return dao.loadAllCelebrants();
	}
	
	@Override
	public List<Celebrant> loadActiveCelebrants() {
		return dao.loadActiveCelebrants();
	}
	
	@Override
	public List<Celebrant> loadAllConfessions() {
		return dao.loadAllConfessions();
	}
	
	@Override
	public List<Celebrant> loadActiveConfessions() {
		return dao.loadActiveConfessions();
	}

	@Override
	public List<IntentionType> loadAllTypes() {
		return dao.loadAllTypes();
	}
	
	@Override
	public List<IntentionTypeDuration> loadAllTypeDuration() {
		return dao.loadAllTypeDuration();
	}
	
	@Override
	public List<Intention> loadIntentions(Date startDate, Date endDate) {
		//date not needed to adjust ni this case
		
		return dao.loadIntentions(new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
	}
	
	private Date adjustDate(Date date) {
		//date in DB is put without timezone, we need to adjust it to make any calls
		
		int milisOffset = TimeZone.getDefault().getOffset(date.getTime());
		
		return new Date(date.getTime() - milisOffset);
	}
	
	@Override
	public List<Intention> loadIntentions(Date date) {
		//Date d = adjustDate(date);
		Date d = date;
		
		return dao.loadIntentions(new java.sql.Date(d.getTime()), new java.sql.Date(d.getTime()));
	}
	
	@Override
	public Intention save(Intention intention) {
		if (intention.getId() == null) {
			//decrease date for new intentions
			
			//intention.setDate(adjustDate(intention.getDate()));
		}
		
		return dao.save(intention);
	}
	
	@Override
	public void delete(long intentionId) {
		dao.delete(dao.get(intentionId));
	}

	@Override
	public Intention get(Long id) {
		return dao.get(id);
	}
}
