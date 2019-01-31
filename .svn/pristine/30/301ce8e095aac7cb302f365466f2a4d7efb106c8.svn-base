package org.parafia.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.parafia.dao.FileDao;
import org.parafia.model.Mail;
import org.parafia.model.MailFile;
import org.springframework.stereotype.Repository;
@Repository("fileDao")
public class FileDaoHibernate extends GenericDaoHibernate<Mail, Long> implements FileDao {
	
	
	public FileDaoHibernate() {
		super(Mail.class);
	}

	public String getLastMailNumber(String year) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		
		Object obj = session.createSQLQuery("select max(cast(split_part(number, '/', 1) as int)) from mails where split_part(number, '/', 2) = ?;").setString(0, year).uniqueResult();
		if (obj != null)
			return String.valueOf(obj);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public Mail get(String path) {
		List<Mail> files = getHibernateTemplate().find("from Mail where path = ? order by registerDate desc", path); 
		if (files.size() > 0) {
			return files.get(0);
		} else {
			return null;
		}
	}
	
	public MailFile getMailFile(long id) {
		return (MailFile)getHibernateTemplate().get(MailFile.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Mail[] getFilesList(String path) {
		if (path == null) {
			log.warn("Path jest nullem");
			return null;
		}
		List<Mail> files = getHibernateTemplate().find("from Mail where path = ? order by registerDate desc", path);
		return files.toArray(new Mail[0]);
	}
	
	@SuppressWarnings("unchecked")
	public Mail[] getLastTenFilesList(Date date, String dir) {
		if (date == null) {
			date = new Date();
		}
		log.debug("Bierzemy ostatnie pliki z katalogu: " + dir +"/%");
		DetachedCriteria criteria = DetachedCriteria.forClass(Mail.class).add(Restrictions.le("registerDate", date))
			.add(Restrictions.like("path", dir + "/%"))
			.addOrder(Order.desc("registerDate"));
		List<Mail> files = getHibernateTemplate().findByCriteria(criteria, 0, 10);
		
		//List<File> files = getHibernateTemplate().find("from File where registerDate <= ?", date);
		return files.toArray(new Mail[0]);
	}
	
	@SuppressWarnings("unchecked")
	public Mail[] getFilesListWithSubDir(String path) {
		if (path == null) {
			log.warn("Path jest nullem");
			return null;
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Criteria crit = session.createCriteria(Mail.class);
        crit.add(Restrictions.like("path",path + "%"));
        crit.addOrder(Order.desc("registerDate"));
        List<Mail> files = crit.list();
        return files.toArray(new Mail[0]);
	}
	
	public void removeMail(Mail mail) {
		getHibernateTemplate().delete(mail);
	}
	
	@SuppressWarnings("unchecked")
	public Mail[] findFiles(String text, Date fromDate, Date toDate) {
		if (StringUtils.isBlank(text) && fromDate == null && toDate == null) {
			log.warn("Text jest nullem lub jest pusty i obie daty sa puste");
			return null;
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String query = "SELECT M.* FROM MAILS M LEFT OUTER JOIN MAIL_FILES MF ON (M.ID = MF.MAIL_ID), ADDRESSEES A WHERE " +
			//"MF.MAIL_ID=M.ID AND " +
			"M.RECEIVER_ID=A.ID AND " +
			(fromDate != null ? "M.REGISTER_DATE >= ? AND " : "") +
			(toDate != null ? "M.REGISTER_DATE <= ? AND " : "") +
			(StringUtils.isBlank(text) ? "" : "(UPPER(M.KEY_WORDS) like UPPER(?) OR UPPER(M.DESCRIPTION_NAME) like UPPER(?) " +
			"OR UPPER(A.FIRST_NAME) like UPPER(?) OR UPPER(A.LAST_NAME) like UPPER(?) " +
			"OR UPPER(A.INSTITUTION) like UPPER(?) OR UPPER(A.POST) like UPPER(?) OR UPPER(A.STREET) like UPPER(?)) AND ");
		query = query.replaceAll("AND $", "");
		
		SQLQuery q = session.createSQLQuery(query + 
			" ORDER BY M.REGISTER_DATE DESC").addEntity(Mail.class);
		
		int i = 0; 
		if (fromDate != null) {
			q.setDate(i++, fromDate);
		}
		if (toDate != null) {
			q.setDate(i++, toDate);
		}
		
		if (!StringUtils.isBlank(text))
			for (int z = 0; z < 7; z++) {
				q.setString(i++, "%" + text + "%");
			}
		
		log.debug("Teraz wlasciwe query");
		List<Mail> files = q.list();

		return files.toArray(new Mail[0]);
	}
	
	public Mail saveMail(Mail mail) {
		log.debug("mail's id: " + mail.getId());
		getHibernateTemplate().merge(mail);
		// necessary to throw a DataIntegrityViolation and catch it in
		// UserManager
		getHibernateTemplate().flush();
		return mail;
	}
}
