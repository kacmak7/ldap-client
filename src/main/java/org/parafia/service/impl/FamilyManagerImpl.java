package org.parafia.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.parafia.dao.FamilyDao;
import org.parafia.dao.GenericDao;
import org.parafia.model.Family;
import org.parafia.model.Person;
import org.parafia.model.dictionary.BaptismPlace;
import org.parafia.model.dictionary.MarriageStatus;
import org.parafia.model.dictionary.Practising;
import org.parafia.model.dictionary.TakePart;
import org.parafia.service.FamilyManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.SortingPagingException;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service("familyManager")
public class FamilyManagerImpl extends GenericManagerImpl<Family, Long> implements FamilyManager {
	@Autowired
	FamilyDao familyDao;

	@Autowired
	public FamilyManagerImpl(@Qualifier("familyDao") GenericDao<Family, Long> dao)
	{
		super.genericDao = dao;
	}
	
	public List<Family> getFamilies() {
		return familyDao.getFamilies();
	}
	
	public List<Family> getFamilies(String surname, String street, String firstNumber) {
		return familyDao.getFamilies(surname, street, firstNumber);
	}
	
	public ExtendedPaginatedList getRecPageFamilies(ExtendedPaginatedList paginatedList, String surname, String street, String firstNumber)
			throws SortingPagingException {
		try {
			List<Family> results = familyDao.getRecPageFamilies(paginatedList.getFirstRecordIndex(), paginatedList.getPageSize(), paginatedList
	            .getSortDirection(), paginatedList.getSortCriterion(), surname, street, firstNumber);
	        paginatedList.setList(results);
	        paginatedList.setTotalNumberOfRows(familyDao.getRecPageCountFamilies(surname, street, firstNumber));
	        //paginatedList.setTotalNumberOfRows(results.size());
	        return paginatedList;
		} catch (DataAccessException ex) {
			throw new SortingPagingException();
		}		
	}
	
	public Family saveFamily(Family family) throws ObjectExistsException {
		try {
			return familyDao.saveFamily(family);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Family '" + family.getSurname() + " " + family.getAddress() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Family '" + family.getSurname() + " " + family.getAddress() + "' already exists!");
        }
	}
	
	public List<MarriageStatus> getMarriageStatuses() {
		return familyDao.getMarriageStatuses();
	}
	
	public List<TakePart> getTakePart() {
		return familyDao.getTakePart();
	}
	
	public Person savePerson(Person person) throws ObjectExistsException {
		try {
			return familyDao.savePerson(person);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Person " + person + " already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Person " + person + " already exists!");
        }
	}
	
	public List<Practising> getPractising() {
		return familyDao.getPractising();
	}
	
	public List<BaptismPlace> getBaptismPlaces() {
		return familyDao.getBaptismPlaces();
	}
}
