package org.parafia.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.parafia.dao.FamilyDao;
import org.parafia.model.Family;
import org.parafia.model.Person;
import org.parafia.model.dictionary.BaptismPlace;
import org.parafia.model.dictionary.MarriageStatus;
import org.parafia.model.dictionary.Practising;
import org.parafia.model.dictionary.TakePart;
import org.springframework.stereotype.Repository;

@Repository("familyDao")
public class FamilyDaoHibernate extends GenericDaoHibernate<Family, Long> implements FamilyDao {	

	public FamilyDaoHibernate() {
		super(Family.class);
	}

	public List<Family> getFamilies() {
		return getHibernateTemplate().loadAll(Family.class);
	}
	
	private String getFamiliesQuery(String surname, String street, String firstNumber/*, String sortCriterion, String sortDirection, int firstResult, int maxResults*/) {
		String query = "from Family as f";
		String and = "";
		if (StringUtils.isNotBlank(surname)) {
			query += " where (f.husband.id in (select id from Person as p where upper(p.surname) like '%"+surname.toUpperCase()+"%') "
					+ "or f.wife.id in (select id from Person as p where upper(p.surname) like '%" + surname.toUpperCase() + "%'))";
			and = " and ";
		}
		if (StringUtils.isNotBlank(street))  {
			if ("".equals(and))
				and = " where ";
			query += and + "upper(f.address.street) like '%"+street.toUpperCase()+"%'";
			and = " and ";
		}
		if (StringUtils.isNotBlank(firstNumber)) {
			if ("".equals(and))
				and = " where ";
			query += and + "f.address.firstNumber='" + firstNumber + "'";
			and = " and ";
		}
		return query;
	}
	
	private String getFamiliesQueryAdditional(String query, String sortCriterion, SortOrderEnum sortDirection, int firstResult, int maxResults) {
		//String query;
		
		if ("surname".equals(sortCriterion) && sortDirection.equals(SortOrderEnum.ASCENDING))
			query += " order by f.husband.surname, f.wife.surname, f.address.street, cast(nullif(regexp_replace(f.address.firstNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.firstNumber, cast(nullif(regexp_replace(f.address.lastNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.lastNumber asc";
		else if ("surname".equals(sortCriterion) && sortDirection.equals(SortOrderEnum.DESCENDING))
			query += " order by f.husband.surname desc, f.wife.surname desc, f.address.street, cast(nullif(regexp_replace(f.address.firstNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.firstNumber, cast(nullif(regexp_replace(f.address.lastNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.lastNumber asc";
		else if ("address.street".equals(sortCriterion) && sortDirection.equals(SortOrderEnum.DESCENDING))
			query += " order by f.address.street desc, cast(nullif(regexp_replace(f.address.firstNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.firstNumber, cast(nullif(regexp_replace(f.address.lastNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.lastNumber, f.husband.surname, f.wife.surname";
		else
			query += " order by f.address.street, cast(nullif(regexp_replace(f.address.firstNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.firstNumber, cast(nullif(regexp_replace(f.address.lastNumber, '^([0-9]*).*$', '\\1'),'') as int), f.address.lastNumber, f.husband.surname, f.wife.surname";
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public List<Family> getFamilies(String surname, String street, String firstNumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Family.class);
		if (surname != null)
			criteria.add(Restrictions.ilike("surname", surname, MatchMode.ANYWHERE));
		if (street != null)
			criteria.add(Restrictions.ilike("address.street", street, MatchMode.ANYWHERE));
		if (firstNumber != null)
			criteria.add(Restrictions.eq("address.firstNumber", firstNumber));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public int getRecPageCountFamilies(String surname, String street, String firstNumber) {
		String query = "select count(*) " + getFamiliesQuery(surname, street, firstNumber);
		
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = session.createQuery(query);
        Long count = (Long) q.uniqueResult();
        return count.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<Family> getRecPageFamilies(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String surname, String street, String firstNumber) {

		String query = getFamiliesQueryAdditional(getFamiliesQuery(surname, street, firstNumber), sortCriterion, sortDirection, firstResult, maxResults);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Query q = session.createQuery(query);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<Family> families = q.list();
		return families;
	}
	
	public Family saveFamily(Family family) {
		log.debug("family's id: " + family.getId());
		getHibernateTemplate().merge(family);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return family;
	}
	
	@Override
	public Family get(Long id) {
		Family family = super.get(id);
		Hibernate.initialize(family.getOthers());
		Hibernate.initialize(family.getChildren());
		Hibernate.initialize(family.getVisits());
		return family;
	}	

	@SuppressWarnings("unchecked")
	public List<MarriageStatus> getMarriageStatuses() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MarriageStatus.class);
		criteria.addOrder(Order.asc("id"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<TakePart> getTakePart() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TakePart.class);
		criteria.addOrder(Order.asc("id"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Person savePerson(Person person) {
		log.debug("person's id: " + person.getId());
        getHibernateTemplate().saveOrUpdate(person);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return person;
	}
	
	public List<Practising> getPractising() {
		return getHibernateTemplate().loadAll(Practising.class);
	}
	
	public List<BaptismPlace> getBaptismPlaces() {
		return getHibernateTemplate().loadAll(BaptismPlace.class);
	}
}
