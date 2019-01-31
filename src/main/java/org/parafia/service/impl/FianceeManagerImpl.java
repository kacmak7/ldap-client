package org.parafia.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.parafia.dao.FianceeDao;
import org.parafia.dao.GenericDao;
import org.parafia.dao.PersonDao;
import org.parafia.model.FianceePair;
import org.parafia.model.Person;
import org.parafia.service.FianceeManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.SortingPagingException;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service("fianceeManager")
public class FianceeManagerImpl extends GenericManagerImpl<FianceePair, Long> implements FianceeManager {
	
	@Autowired
	FianceeDao fianceeDao;
	@Autowired
	PersonDao personDao;
	
	@Autowired
	public FianceeManagerImpl(@Qualifier("fianceeDao") GenericDao<FianceePair, Long> dao)
	{
		super.genericDao = dao;
	}
	
	@Override
	public FianceePair saveFianceePair(FianceePair fp) throws ObjectExistsException {
		try {
			return fianceeDao.saveFianceePair(fp);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("FianceePair '" + fp.getFianceeHe() + " " + fp.getFianceeShe() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("FianceePair '" + fp.getFianceeHe() + " " + fp.getFianceeShe() + "' already exists!");
        }
	}
	
	@Override
	public FianceePair getFianceePair(Long id) {
		return fianceeDao.getFianceePair(id);
	}
	
	/**
	 * Zamiast tego stosowac getFianceePair(Long id)
	 */
	@Override
	@Deprecated
	public FianceePair get(Long id) {
		FianceePair fp = super.get(id);
			
		//obejscie problemu (Father nie laduje automatycznie Family??!! Mother tak samo)
		fp.setFianceeHe(personDao.getPerson(fp.getFianceeHe().getId()));
		fp.setFianceeShe(personDao.getPerson(fp.getFianceeShe().getId()));
		return fp;
	}
	
	@Override
	public int getMaxProtocoleNumber(int year)  {
		return fianceeDao.getMaxProtocoleNumber(year);
	}
	
	@Override
	public FianceePair getFianceePairForPerson(Long id) {
		return fianceeDao.getFianceePairForPerson(id);
	}
	
	@Override
	public ExtendedPaginatedList getRecPageFianceePairs(ExtendedPaginatedList paginatedList, String firstName, String surname, int year)
			throws SortingPagingException {
		try {
			List<FianceePair> results = fianceeDao.getRecPageFianceePairs(paginatedList.getFirstRecordIndex(), paginatedList.getPageSize(), paginatedList
		        .getSortDirection(), paginatedList.getSortCriterion(), firstName, surname, year);
		    paginatedList.setList(results);
		    paginatedList.setTotalNumberOfRows(fianceeDao.getRecPageCountFianceePairs(firstName, surname, year));
		    return paginatedList;
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			throw new SortingPagingException();
		}		
	}
}
