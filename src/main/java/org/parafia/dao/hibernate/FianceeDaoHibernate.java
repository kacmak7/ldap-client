package org.parafia.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.parafia.dao.FianceeDao;
import org.parafia.model.Child;
import org.parafia.model.Family;
import org.parafia.model.FianceePair;
import org.parafia.model.Parent;
import org.parafia.model.Person;
import org.parafia.util.DateUtil;
import org.springframework.stereotype.Repository;

@Repository("fianceeDao")
public class FianceeDaoHibernate extends GenericDaoHibernate<FianceePair, Long> implements FianceeDao {
	
	public FianceeDaoHibernate() {
		super(FianceePair.class);
	}

	public FianceePair saveFianceePair(FianceePair fp) {
		log.debug("fianceePair's id: " + fp.getId());
		//getHibernateTemplate().saveOrUpdate(fp);
		//getHibernateTemplate().merge(fp);
		fp = (FianceePair)getHibernateTemplate().merge(fp);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return fp;
	}
	
	@SuppressWarnings("unchecked")
	public FianceePair getFianceePairForPerson(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FianceePair.class);
		
        criteria.add(Expression.or(Expression.eq("fianceeHe.id", id), Expression.eq("fianceeShe.id", id)));
        
        List<FianceePair> results = getHibernateTemplate().findByCriteria(criteria);
		if (results.size() > 0 && results.get(0) != null) {
			return results.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public FianceePair getFianceePair(Long id) {
		FianceePair fp = (FianceePair)getHibernateTemplate().get(FianceePair.class, id);
		if (fp.getFianceeHe() instanceof Child)
			Hibernate.initialize(((Child)fp.getFianceeHe()).getFamily());
		if (fp.getFianceeHe() instanceof Parent) {
			log.debug("Parent");
			//Hibernate.initialize(((Parent)person).getFamily());
			
			//chwilowe obejscie problemu gdy hibernate nie znalezl rodziny do parenta (zawsze husbanda), nie wiem dlaczego zapytanie wywoluje taka funkcje:
			//select person0_.id as id6_19_, person0_.first_name as first3_6_19_, family17_.id as id5_16_
			//from persons person0_ left outer join families family17_ on person0_.id=family17_.wife_id where person0_.id in (4541, 4542);
			Parent parent = (Parent)fp.getFianceeHe();
			if (parent.getFamily() == null) {
				List<Family> families = getHibernateTemplate().find("from Family f where f.husband.id=? or f.wife.id=?", new Object[]{Long.valueOf(parent.getId()), Long.valueOf(parent.getId())});
				if (families.size() > 0)
					parent.setFamily(families.get(0));
				//Family family = getHibernateTemplate().
			}
		}
		if (fp.getFianceeShe() instanceof Child)
			Hibernate.initialize(((Child)fp.getFianceeShe()).getFamily());
		if (fp.getFianceeShe() instanceof Parent) {
			log.debug("Parent");
			//Hibernate.initialize(((Parent)person).getFamily());
			
			//chwilowe obejscie problemu gdy hibernate nie znalezl rodziny do parenta (zawsze husbanda), nie wiem dlaczego zapytanie wywoluje taka funkcje:
			//select person0_.id as id6_19_, person0_.first_name as first3_6_19_, family17_.id as id5_16_
			//from persons person0_ left outer join families family17_ on person0_.id=family17_.wife_id where person0_.id in (4541, 4542);
			Parent parent = (Parent)fp.getFianceeShe();
			if (parent.getFamily() == null) {
				List<Family> families = getHibernateTemplate().find("from Family f where f.husband.id=? or f.wife.id=?", new Object[]{Long.valueOf(parent.getId()), Long.valueOf(parent.getId())});
				if (families.size() > 0)
					parent.setFamily(families.get(0));
				//Family family = getHibernateTemplate().
			}
		}
		return fp;
	}
	
	@SuppressWarnings("unchecked")
	public int getMaxProtocoleNumber(int year) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FianceePair.class);
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.max("protocoleNumber"));
        criteria.setProjection(projList);
        criteria.add(Expression.between("protocoleDate", DateUtil.getFirstDayOfTheYear(year), DateUtil.getLastDayOfTheYear(year)));
        
        List<Integer> results = getHibernateTemplate().findByCriteria(criteria);
		if (results.size() > 0 && results.get(0) != null) {
			return results.get(0);
		}
		return 0;
	}

	public int getRecPageCountFianceePairs(String firstName, String surname, int year) {
		String query = "from FianceePair fp";
		String and = "";
		if (StringUtils.isNotEmpty(firstName)) {
			if(and.equals("")) 
				and = " where ";
			query += and + "(upper(fp.fianceeHe.firstName) like '%"+firstName.toUpperCase()+"%' ";
			query += "or upper(fp.fianceeShe.firstName) like '%"+firstName.toUpperCase()+"%') ";
			and = " and ";
		}
		if (StringUtils.isNotEmpty(surname)) {
			if(and.equals("")) 
				and = " where ";
			query += and + "(upper(fp.fianceeHe.surname) like '%"+surname.toUpperCase()+"%' ";
			query += "or upper(fp.fianceeShe.surname) like '%"+surname.toUpperCase()+"%') ";
			and = " and ";
		}
		if (year > 0) {
			if(and.equals("")) 
				and = " where ";
			query += and + "substring(to_char(fp.marriageDate, 'yyyy-mm-dd'), 1, 4) = '" + year + "'";
		}
        int count = getHibernateTemplate().find(query).size();
        
        return count;
	}


	@SuppressWarnings("unchecked")
	public List<FianceePair> getRecPageFianceePairs(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String firstName, String surname, int year) {
		String query = "from FianceePair fp";
		String and = "";
		if (StringUtils.isNotEmpty(firstName)) {
			if(and.equals("")) 
				and = " where ";
			query += and + "(upper(fp.fianceeHe.firstName) like '%"+firstName.toUpperCase()+"%' ";
			query += "or upper(fp.fianceeShe.firstName) like '%"+firstName.toUpperCase()+"%') ";
			and = " and ";
		}
		if (StringUtils.isNotEmpty(surname)) {
			if(and.equals("")) 
				and = " where ";
			query += and + "(upper(fp.fianceeHe.surname) like '%"+surname.toUpperCase()+"%' ";
			query += "or upper(fp.fianceeShe.surname) like '%"+surname.toUpperCase()+"%') ";
			and = " and ";
		}
		if (year > 0) {
			if(and.equals("")) 
				and = " where ";
			query += and + "substring(to_char(fp.marriageDate, 'yyyy-mm-dd'), 1, 4) = '" + year + "'";
		}
		
		if(sortCriterion != null && sortDirection != null) {
			query += " order by fp." + sortCriterion + (sortDirection.equals(SortOrderEnum.DESCENDING)?" desc":"");
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(query);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<FianceePair> fianceePairs = q.list();

		return fianceePairs;
	}
}