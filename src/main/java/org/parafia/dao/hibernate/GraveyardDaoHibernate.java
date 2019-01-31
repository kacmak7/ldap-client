package org.parafia.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.parafia.dao.GraveyardDao;
import org.parafia.model.Columbarium;
import org.parafia.model.Grave;
import org.parafia.model.GraveFile;
import org.parafia.model.Niche;
import org.parafia.model.dictionary.GraveDoubled;
import org.parafia.model.dictionary.GraveGrounded;
import org.parafia.model.dictionary.GraveOwned;
import org.parafia.service.exceptions.MoreThanOneGeomException;
import org.springframework.stereotype.Repository;
@Repository("graveyardDao")
public class GraveyardDaoHibernate extends GenericDaoHibernate<Grave, Long>
		implements GraveyardDao {
	

	public GraveyardDaoHibernate() {
		super(Grave.class);
	}

	public List<GraveDoubled> getGraveDoubled() {
		return getHibernateTemplate().loadAll(GraveDoubled.class);
	}

	public List<GraveOwned> getGraveOwned() {
		return getHibernateTemplate().loadAll(GraveOwned.class);
	}

	public List<GraveGrounded> getGraveGrounded() {
		return getHibernateTemplate().loadAll(GraveGrounded.class);
	}
	
	public GraveFile getFile(long fileId) {
		return (GraveFile) getHibernateTemplate().get(GraveFile.class, fileId);
	}

	public Grave getGrave(long graveId) {
		Grave grave = (Grave) getHibernateTemplate().get(Grave.class, graveId);
		if (grave != null) {
			Hibernate.initialize(grave.getFiles());
			Hibernate.initialize(grave.getLevels());
			Hibernate.initialize(grave.getPersons());
		}
		return grave;
	}
	
	@SuppressWarnings("unchecked")
	public Grave getGraveWithNumber(int number) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Grave.class);
		criteria.add(Restrictions.eq("number", number));
		List<Grave> graves = getHibernateTemplate().findByCriteria(criteria);
		if (graves.size() > 0) {
			Grave grave = graves.get(0);
			Hibernate.initialize(grave.getFiles());
			Hibernate.initialize(grave.getLevels());
			Hibernate.initialize(grave.getPersons());
			return grave;
		} else
			return null;
	}
	
	public List<Grave> loadAll() {
		return getHibernateTemplate().loadAll(Grave.class);
	}
	
	public List<Grave> loadAllWithFiles() {
		List<Grave> graves = getHibernateTemplate().loadAll(Grave.class);
		for (Grave grave : graves) {
			Hibernate.initialize(grave.getFiles());
			Hibernate.initialize(grave.getPoints());
		}
		return graves;
	}
	
	public Grave getGraveWithGeom(String postgisString) throws MoreThanOneGeomException {
		//postgisString is something like POLYGON((...))
		//ST_OrderingEquals tests whether two geometries are geometrically identical
		logger.info("Looking for a geometry: " + postgisString);
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery("findGraveWithGeometryLikeSQL").setString("postgisString", postgisString);
		
		Grave grave = (Grave)query.uniqueResult();
		return grave;
	}
	
	public String getGeomForGrave(long graveId) throws MoreThanOneGeomException {
		//ST_OrderingEquals tests whether two geometries are geometrically identical
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		//Query query = session.getNamedQuery("findGeometryForGraveSQL").setLong("graveId", graveId);
		
		Query query = session.createSQLQuery("select ST_AsText(g.the_geom) from graves g where g.id=" + graveId);
		
		String geom = (String)query.uniqueResult();
		return geom;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSectors() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Grave.class);
		criteria.setProjection(Projections.distinct(Projections.property("sector")));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Grave> loadAllFromSector(String sector) {
		return getHibernateTemplate().find("from Grave where sector=?", sector);
	}

	public Grave saveGrave(Grave grave) {
		log.debug("grave's id: " + grave.getId());
		grave = (Grave)getHibernateTemplate().merge(grave);
		// necessary to throw a DataIntegrityViolation and catch it in
		// UserManager
		getHibernateTemplate().flush();
		
		return grave;
	}
	
	public void executeCustomQuery(String sql) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		//session.beginTransaction();
		session.createSQLQuery(sql).executeUpdate();
		//session.getTransaction().commit();
		//session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Grave> findGraves(String graveOwnerFirstName, String graveOwnerSurname,
			String deadPersonFirstName, String deadPersonSurname, String graveNumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Grave.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (StringUtils.isNotEmpty(graveOwnerFirstName))
			criteria.add(Restrictions.ilike("owner.firstName", graveOwnerFirstName, MatchMode.ANYWHERE));
		if (StringUtils.isNotEmpty(graveOwnerSurname))
			criteria.add(Restrictions.ilike("owner.surname", graveOwnerSurname, MatchMode.ANYWHERE));
		if (StringUtils.isNotEmpty(graveNumber))
			criteria.add(Restrictions.eq("number", Integer.parseInt(graveNumber)));
		
		if (StringUtils.isNotEmpty(deadPersonFirstName) && StringUtils.isNotEmpty(deadPersonSurname)) {
			criteria.createCriteria("persons").add(Restrictions.ilike("firstName", deadPersonFirstName, MatchMode.ANYWHERE))
				.add(Restrictions.ilike("surname", deadPersonSurname, MatchMode.ANYWHERE));
		} else if (StringUtils.isNotEmpty(deadPersonFirstName)) {
			criteria.createCriteria("persons").add(Restrictions.ilike("firstName", deadPersonFirstName, MatchMode.ANYWHERE));
		} else if (StringUtils.isNotEmpty(deadPersonSurname)) {
			criteria.createCriteria("persons").add(Restrictions.ilike("surname", deadPersonSurname, MatchMode.ANYWHERE));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public List<Grave> findGravesWithPersons(String graveOwnerFirstName, String graveOwnerSurname,
			String deadPersonFirstName, String deadPersonSurname, String graveNumber) {
		List<Grave> graves = this.findGraves(graveOwnerFirstName, graveOwnerSurname, deadPersonFirstName, deadPersonSurname, graveNumber);
		for (Grave grave : graves) {
			Hibernate.initialize(grave.getPersons());
		}
		return graves;
	}
	
	@SuppressWarnings("unchecked")
	public List<Grave> loadInvalid(int currentYear) {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(Grave.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.lt("validTo", currentYear));
		return getHibernateTemplate().findByCriteria(criteria);*/
		List<Grave> graves = getHibernateTemplate().find("from Grave where validTo < ?", currentYear);
		for (Grave grave : graves) {
			Hibernate.initialize(grave.getPersons());
		}
		
		return graves;
	}
	
	public void deleteGrave(Grave grave) {
		/*for (GravePoint point : grave.getPoints()) {
			getHibernateTemplate().delete(point);
		}*/
		getHibernateTemplate().delete(grave);
		getHibernateTemplate().flush();
	}
	
	public List<Columbarium> loadColumbariums() {
		return getHibernateTemplate().loadAll(Columbarium.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Niche> loadNiches(long columbariumId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Niche.class);
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("columbarium.id", columbariumId));
		criteria.addOrder(Order.asc("number"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public List<Niche> loadNichesWithPersons(String ownerFirstName, String ownerSurname,
			String deadPersonFirstName, String deadPersonSurname, String number) {
		List<Niche>  niches = this.loadNiches(ownerFirstName, ownerSurname, deadPersonFirstName, deadPersonSurname, number);
		for (Niche niche : niches) {
			Hibernate.initialize(niche.getPersons());
		}
		return niches;
	}
	
	@SuppressWarnings("unchecked")
	public List<Niche> loadNiches(String ownerFirstName, String ownerSurname,
			String deadPersonFirstName, String deadPersonSurname, String number) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Niche.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (StringUtils.isNotEmpty(ownerFirstName))
			criteria.add(Restrictions.ilike("owner.firstName", ownerFirstName, MatchMode.ANYWHERE));
		if (StringUtils.isNotEmpty(ownerSurname))
			criteria.add(Restrictions.ilike("owner.surname", ownerSurname, MatchMode.ANYWHERE));
		if (StringUtils.isNotEmpty(number))
			criteria.add(Restrictions.eq("number", Integer.parseInt(number)));
		
		if (StringUtils.isNotEmpty(deadPersonFirstName) && StringUtils.isNotEmpty(deadPersonSurname)) {
			criteria.createCriteria("persons").add(Restrictions.ilike("firstName", deadPersonFirstName, MatchMode.ANYWHERE))
				.add(Restrictions.ilike("surname", deadPersonSurname, MatchMode.ANYWHERE));
		} else if (StringUtils.isNotEmpty(deadPersonFirstName)) {
			criteria.createCriteria("persons").add(Restrictions.ilike("firstName", deadPersonFirstName, MatchMode.ANYWHERE));
		} else if (StringUtils.isNotEmpty(deadPersonSurname)) {
			criteria.createCriteria("persons").add(Restrictions.ilike("surname", deadPersonSurname, MatchMode.ANYWHERE));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Niche getNiche(long nicheId) {
		Niche niche = (Niche) getHibernateTemplate().get(Niche.class, nicheId);
		if (niche != null) {
			Hibernate.initialize(niche.getFiles());
			Hibernate.initialize(niche.getPersons());
		}
		return niche;
	}
	
	@SuppressWarnings("unchecked")
	public Niche getNicheWithNumber(int number) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Niche.class);
		criteria.add(Restrictions.eq("number", number));
		List<Niche> niches = getHibernateTemplate().findByCriteria(criteria);
		if (niches.size() > 0) {
			Niche niche = niches.get(0);
			Hibernate.initialize(niche.getFiles());
			Hibernate.initialize(niche.getPersons());
			return niche;
		} else
			return null;
	}
	
	public Niche saveNiche(Niche niche) {
		log.debug("niche's id: " + niche.getId());
		niche = (Niche)getHibernateTemplate().merge(niche);
		// necessary to throw a DataIntegrityViolation and catch it in
		// GraveyardManager
		getHibernateTemplate().flush();
		
		return niche;
	}

	@Override
	public List<Niche> loadNiches()
	{
		return (List<Niche>) getHibernateTemplate().loadAll(Niche.class);
	}

	@Override
	public void evict(Object obj)
	{
		getHibernateTemplate().evict(obj);
		
	}
	
	
}