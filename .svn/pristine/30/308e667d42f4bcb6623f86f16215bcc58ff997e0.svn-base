package org.parafia.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.parafia.Constants;
import org.parafia.dao.GenericDao;
import org.parafia.dao.PersonDao;
import org.parafia.model.Person;
import org.parafia.service.PersonManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.SortingPagingException;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
@Service("personManager")
public class PersonManagerImpl extends GenericManagerImpl<Person, Long> implements PersonManager {
	@Autowired
	private PersonDao dao;
	
	@Autowired
	public PersonManagerImpl(@Qualifier("personDao") GenericDao<Person, Long> dao) {
		super.genericDao = dao;
	}

	@Override
	public Integer[] getBaptismYears() {
		List<Integer> years = new ArrayList<Integer>();
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
		int start = dao.getMinBaptismYear();
		if (start == 0) {
			return new Integer[]{};
		} else {
			int end = Integer.valueOf(df.format(new Date()));
			
			for (int i = start; i <= end; i++) {
				years.add(i);
			}
			return years.toArray(new Integer[0]);
		}
	}
	
	@Override
	public Integer[] getConfirmationYears() {
		List<Integer> years = new ArrayList<Integer>();
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
		int start = dao.getMinConfirmationYear();
		if (start == 0) {
			return new Integer[]{};
		} else {
			int end = Integer.valueOf(df.format(new Date()));
			
			for (int i = start; i <= end; i++) {
				years.add(i);
			}
			return years.toArray(new Integer[0]);
		}
	}
	
	@Override
	public Integer[] getDeathYears() {
		List<Integer> years = new ArrayList<Integer>();
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
		int start = dao.getMinDeathYear();
		if (start == 0) {
			return new Integer[]{};
		} else {
			int end = Integer.valueOf(df.format(new Date()));
			
			for (int i = start; i <= end; i++) {
				years.add(i);
			}
			return years.toArray(new Integer[0]);
		}
	}
	
	@Override
	public Integer[] getBurialYears() {
		List<Integer> years = new ArrayList<Integer>();
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
		int start = dao.getMinBurialYear();
		if (start == 0) {
			return new Integer[]{};
		} else {
			int end = Integer.valueOf(df.format(new Date()));
			
			for (int i = start; i <= end; i++) {
				years.add(i);
			}
			return years.toArray(new Integer[0]);
		}
	}
	
	@Override
	public Integer[] getFianceesYears() {
		List<Integer> years = new ArrayList<Integer>();
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
		int start = dao.getMinFianceesYear();
		if (start == 0) {
			return new Integer[]{};
		} else {
			int end = Integer.valueOf(df.format(new Date()));
			
			for (int i = start; i <= end; i++) {
				years.add(i);
			}
			return years.toArray(new Integer[0]);
		}
	}
	
	@Override
	public List<Person> getPersons(String firstName, String surname) {
		return dao.getPersons(firstName, surname);
	}
	
	@Override
	public List<Person> getPersons() {
		return dao.getPersons();
	}
	
	@Override
	public Person getPerson(long id) {
		return dao.getPerson(id);
	}
	
	@Override
	public List<Person> getPersonsWithoutId(String firstName, String surname, Long id) {
		return dao.getPersonsWithoutId(firstName, surname, id);
	}
	
	@Override
	public List<Person> getAllWithoutId(Long id) {
		return dao.getAllWithoutId(id);
	}
	
	@Override
	public ExtendedPaginatedList getRecPageBaptismes(ExtendedPaginatedList paginatedList, String surname, String firstName, String date)
			throws SortingPagingException {
		try {
			List<Person> results = dao.getRecPageBaptismes(paginatedList.getFirstRecordIndex(), paginatedList.getPageSize(), paginatedList
	            .getSortDirection(), paginatedList.getSortCriterion(), surname, firstName, date);
	        paginatedList.setList(results);
	        paginatedList.setTotalNumberOfRows(dao.getRecPageCountBaptismes(surname, firstName, date));
	        return paginatedList;
		} catch (DataAccessException ex) {
			throw new SortingPagingException();
		}		
	}
	
	@Override
	public ExtendedPaginatedList getRecPageConfirmations(ExtendedPaginatedList paginatedList, String surname,
			String firstName, String date) throws SortingPagingException {
		try {
			List<Person> results = dao.getRecPageConfirmations(paginatedList.getFirstRecordIndex(), paginatedList.getPageSize(), paginatedList
	            .getSortDirection(), paginatedList.getSortCriterion(), surname, firstName, date);
	        paginatedList.setList(results);
	        paginatedList.setTotalNumberOfRows(dao.getRecPageCountConfirmations(surname, firstName, date));
	        return paginatedList;
		} catch (DataAccessException ex) {
			throw new SortingPagingException();
		}		
	}
	
	@Override
	public ExtendedPaginatedList getRecPageDeaths(ExtendedPaginatedList paginatedList, String surname, String firstName,
			String date, String burialYear) throws SortingPagingException {
		try {
			List<Person> results = dao.getRecPageDeaths(paginatedList.getFirstRecordIndex(), paginatedList.getPageSize(), paginatedList
	            .getSortDirection(), paginatedList.getSortCriterion(), surname, firstName, date, burialYear);
	        paginatedList.setList(results);
	        paginatedList.setTotalNumberOfRows(dao.getRecPageCountDeaths(surname, firstName, date, burialYear));
	        return paginatedList;
		} catch (DataAccessException ex) {
			throw new SortingPagingException();
		}	
	}
	
	@Override
	public Person savePerson(Person person) throws ObjectExistsException {
		try {
			return dao.savePerson(person);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Person '" + person.getFullName() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Person '" + person.getFullName() + "' already exists!");
        }
	}
}
