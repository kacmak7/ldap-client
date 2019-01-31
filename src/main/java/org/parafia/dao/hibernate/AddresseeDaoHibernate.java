package org.parafia.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.parafia.dao.AddresseeDao;
import org.parafia.model.Addressee;
import org.springframework.stereotype.Repository;

@Repository("addresseeDao")
public class AddresseeDaoHibernate extends GenericDaoHibernate<Addressee, Long> implements AddresseeDao {
	public AddresseeDaoHibernate() {
		super(Addressee.class);
	}
	
    public Addressee saveAddressee(Addressee addressee) {
        log.debug("addressee's id: " + addressee.getId());
        getHibernateTemplate().saveOrUpdate(addressee);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return addressee;
    }
    
    @SuppressWarnings("unchecked")
	public List<Character> getFirstLetters() {
    	Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(
			"select distinct * from " +
			" (SELECT substr(last_name, 1, 1) FROM ADDRESSEES WHERE last_name != ''" +
			" UNION" +
			" SELECT substr(institution, 1, 1) FROM ADDRESSEES WHERE institution != '')" +
			" as first_letter");
		List<Character> chars = query.list();
		return chars;
    }
    
    @SuppressWarnings("unchecked")
	public List<Addressee> getAddresseesWithFirstLetter(Character letter) {
    	List<Addressee> addressees = getHibernateTemplate().find("from Addressee ad where substr(ad.lastName, 1, 1) = ? or substr(ad.institution, 1, 1) = ?", new Object[] {letter.toString(), letter.toString()});
    	return addressees;    	
    }
}
