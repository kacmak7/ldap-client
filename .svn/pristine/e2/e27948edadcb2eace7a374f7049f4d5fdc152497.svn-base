package org.parafia.dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.parafia.Constants;
import org.parafia.dao.PersonDao;
import org.parafia.model.Child;
import org.parafia.model.Family;
import org.parafia.model.FianceePair;
import org.parafia.model.Other;
import org.parafia.model.Parent;
import org.parafia.model.Person;
import org.parafia.util.DateUtil;
import org.springframework.stereotype.Repository;

@Repository("personDao")
public class PersonDaoHibernate extends GenericDaoHibernate<Person, Long> implements PersonDao {
		
	
	public PersonDaoHibernate() {
		super(Person.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int getMinBaptismYear() {
		List<Person> persons = getHibernateTemplate().find("from Person p where p.remaining.baptism.date = (select min(remaining.baptism.date) from Person " +
				"where char_length(remaining.baptism.date) >= 4)");
		/*DetachedCriteria criteria = DetachedCriteria.forClass(Person.class);
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.min("remaining.baptism.date"));
        criteria.setProjection(projList);
        List<Person> persons = getHibernateTemplate().findByCriteria(criteria);*/
		
		//Person person = (Person)getHibernateTemplate().find("from Person p where p.remaining.baptism.date = ()");
		if (persons != null && persons.size() > 0) {
			//DateFormat df= new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
			return Integer.valueOf(persons.get(0).getRemaining().getBaptism().getDate().substring(0, 4));
		} else
			return 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int getMinConfirmationYear() {
		List<Person> persons = getHibernateTemplate().find("from Person p where p.remaining.confirmation.date = (select min(remaining.confirmation.date) from Person)");
		if (persons != null && persons.size() > 0) {
			DateFormat df= new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
			return Integer.valueOf(df.format(persons.get(0).getRemaining().getConfirmation().getDate()));
		} else
			return 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int getMinDeathYear() {
		List<Person> persons = getHibernateTemplate().find("from Person p where p.remaining.death.date = (select min(remaining.death.date) from Person)");
		if (persons != null && persons.size() > 0) {
			DateFormat df= new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
			return Integer.valueOf(df.format(persons.get(0).getRemaining().getDeath().getDate()));
		} else
			return 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int getMinBurialYear() {
		List<Person> persons = getHibernateTemplate().find("from Person p where p.remaining.death.burialDate = (select min(remaining.death.burialDate) from Person)");
		if (persons != null && persons.size() > 0) {
			DateFormat df= new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
			return Integer.valueOf(df.format(persons.get(0).getRemaining().getDeath().getBurialDate()));
		} else
			return 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int getMinFianceesYear() {
		List<FianceePair> pairs = getHibernateTemplate().find("from FianceePair fp where fp.marriageDate = (select min(marriageDate) from FianceePair)");
		if (pairs != null && pairs.size() > 0) {
			DateFormat df= new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
			return Integer.valueOf(df.format(pairs.get(0).getMarriageDate()));
		} else
			return 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getPersons(String firstName, String surname) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Person.class);
		if (firstName != null)
			criteria.add(Restrictions.ilike("firstName", firstName, MatchMode.ANYWHERE));
		if (surname != null)
			criteria.add(Restrictions.ilike("surname", surname, MatchMode.ANYWHERE));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public List<Person> getPersons() {
		List<Person> persons = getHibernateTemplate().loadAll(Person.class);
		return persons;
	}
	
	@Override
	public Person getPerson(long id) {
		Person person = (Person)getHibernateTemplate().get(Person.class, id);
		if (person instanceof Parent) {
			log.debug("Parent");
			//Hibernate.initialize(((Parent)person).getFamily());
			
			//TODO:
			//chwilowe obejscie problemu gdy hibernate nie znalezl rodziny do parenta (zawsze husbanda), nie wiem dlaczego zapytanie wywoluje taka funkcje:
			//select person0_.id as id6_19_, person0_.first_name as first3_6_19_, family17_.id as id5_16_
			//from persons person0_ left outer join families family17_ on person0_.id=family17_.wife_id where person0_.id in (4541, 4542);
			Parent parent = (Parent)person;
			if (parent.getFamily() == null) {
				List<Family> families = getHibernateTemplate().find("from Family f where f.husband.id=? or f.wife.id=?", new Object[]{Long.valueOf(id), Long.valueOf(id)});
				if (families.size() > 0)
					parent.setFamily(families.get(0));
				//Family family = getHibernateTemplate().
			}
		} else if (person instanceof Child) {
			log.debug("Child");
			Hibernate.initialize(((Child)person).getFamily());
		} else if (person instanceof Other) {
			log.debug("Child");
			Hibernate.initialize(((Other)person).getFamily());
		}
		return person;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getPersonsWithoutId(String firstName, String surname, Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Person.class);
		if (firstName != null)
			criteria.add(Restrictions.ilike("firstName", firstName, MatchMode.ANYWHERE));
		if (surname != null)
			criteria.add(Restrictions.ilike("surname", surname, MatchMode.ANYWHERE));
		criteria.add(Restrictions.ne("id", id));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getAllWithoutId(Long id) {
		return getHibernateTemplate().find("from Person p where p.id != ?", id);
	}
	
	@Override
	public int getRecPageCountBaptismes(String surname, String firstName, String date) {
        String query = "from Person p where p.remaining.baptism.id != null";
   
        String and = " and ";
        if(StringUtils.isNotBlank(surname)) {
        	query += and + "upper(p.surname) like '%" + surname.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(firstName)) {
        	query += and + "upper(p.firstName) like '%" + firstName.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(date) && !date.equals("0")) {
        	query += and + "substring(p.remaining.baptism.date, 1, 4) = '" + date + "'";
        }
        int count = getHibernateTemplate().find(query).size();
        return count;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getRecPageBaptismes(int firstResult, int maxResults, SortOrderEnum sortDirection,
		String sortCriterion, String surname, String firstName, String date) {
        String query = "from Person p where p.remaining.baptism.id != null";
        
        String and = " and ";
        if(StringUtils.isNotBlank(surname)) {
        	query += and + "upper(p.surname) like '%" + surname.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(firstName)) {
        	query += and + "upper(p.firstName) like '%" + firstName.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(date) && !date.equals("0")) {
        	query += and + "substring(p.remaining.baptism.date, 1, 4) = '" + date + "'";
        }

        if(sortCriterion != null && sortDirection != null) {
        	query += " order by p." + sortCriterion + (sortDirection.equals(SortOrderEnum.DESCENDING)?" desc":"");
        }
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(query);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<Person> persons = q.list();
		return persons;
	}
	
	@Override
	public int getRecPageCountConfirmations(String surname, String firstName, String date) {
        String query = "from Person p where p.remaining.confirmation.id != null";
        
        String and = " and ";
        if(StringUtils.isNotBlank(surname)) {
        	query += and + "upper(p.surname) like '%" + surname.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(firstName)) {
        	query += and + "upper(p.firstName) like '%" + firstName.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(date) && !date.equals("0")) {
        	query += and + "substring(to_char(p.remaining.confirmation.date, 'yyyy-mm-dd'), 1, 4) = '" + date + "'";
        }
        int count = getHibernateTemplate().find(query).size();
        return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getRecPageConfirmations(int firstResult, int maxResults, SortOrderEnum sortDirection,
			String sortCriterion, String surname, String firstName, String date) {
	        String query = "from Person p where p.remaining.confirmation.id != null";
	        
	        String and = " and ";
	        if(StringUtils.isNotBlank(surname)) {
	        	query += and + "upper(p.surname) like '%" + surname.toUpperCase() + "%'";
	        }
	        if(StringUtils.isNotBlank(firstName)) {
	        	query += and + "upper(p.firstName) like '%" + firstName.toUpperCase() + "%'";
	        }
	        if(StringUtils.isNotBlank(date) && !date.equals("0")) {
	        	query += and + "substring(to_char(p.remaining.confirmation.date, 'yyyy-mm-dd'), 1, 4) = '" + date + "'";
	        }

	        if(sortCriterion != null && sortDirection != null) {
	        	query += " order by p." + sortCriterion + (sortDirection.equals(SortOrderEnum.DESCENDING)?" desc":"");
	        }
			
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query q = session.createQuery(query);
			q.setFirstResult(firstResult);
			q.setMaxResults(maxResults);
			List<Person> persons = q.list();
			return persons;
	}
	
	@Override
	public int getRecPageCountDeaths(String surname, String firstName, String date, String burialYear) {
        String query = "from Person p where p.remaining.death.id != null";
        
        String and = " and ";
        if(StringUtils.isNotBlank(surname)) {
        	query += and + "upper(p.surname) like '%" + surname.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(firstName)) {
        	query += and + "upper(p.firstName) like '%" + firstName.toUpperCase() + "%'";
        }
        if(StringUtils.isNotBlank(date) && !date.equals("0")) {
        	query += and + "substring(to_char(p.remaining.death.date, 'yyyy-mm-dd'), 1, 4) = '" + date + "'";
        }
        if(StringUtils.isNotBlank(burialYear) && !burialYear.equals("0")) {
        	query += and + "substring(to_char(p.remaining.death.burialDate, 'yyyy-mm-dd'), 1, 4) = '" + burialYear + "'";
        }
        int count = getHibernateTemplate().find(query).size();
        return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getRecPageDeaths(int firstResult, int maxResults, SortOrderEnum sortDirection,
			String sortCriterion, String surname, String firstName, String date, String burialYear) {
	        String query = "from Person p where p.remaining.death.id != null";
	        
	        String and = " and ";
	        if(StringUtils.isNotBlank(surname)) {
	        	query += and + "upper(p.surname) like '%" + surname.toUpperCase() + "%'";
	        }
	        if(StringUtils.isNotBlank(firstName)) {
	        	query += and + "upper(p.firstName) like '%" + firstName.toUpperCase() + "%'";
	        }
	        if(StringUtils.isNotBlank(date) && !date.equals("0")) {
	        	query += and + "substring(to_char(p.remaining.death.date, 'yyyy-mm-dd'), 1, 4) = '" + date + "'";
	        }
	        if(StringUtils.isNotBlank(burialYear) && !burialYear.equals("0")) {
	        	query += and + "substring(to_char(p.remaining.death.burialDate, 'yyyy-mm-dd'), 1, 4) = '" + burialYear + "'";
	        }

	        if(sortCriterion != null && sortDirection != null) {
	        	query += " order by p." + sortCriterion + (sortDirection.equals(SortOrderEnum.DESCENDING)?" desc":"");
	        }
			
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query q = session.createQuery(query);
			q.setFirstResult(firstResult);
			q.setMaxResults(maxResults);
			List<Person> persons = q.list();
			return persons;
	}
	
	@Override
	public Person savePerson(Person person) {
		log.debug("person's id: " + person.getId());
        getHibernateTemplate().saveOrUpdate(person);
		person = (Person)getHibernateTemplate().merge(person);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return person;
	}
}
