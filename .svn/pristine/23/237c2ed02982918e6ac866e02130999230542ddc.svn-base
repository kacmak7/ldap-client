package org.parafia.service;

import java.util.List;

import org.parafia.model.Person;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.SortingPagingException;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;

public interface PersonManager extends GenericManager<Person, Long> {

	ExtendedPaginatedList getRecPageBaptismes(ExtendedPaginatedList paginatedList, String surname, String firstName, String date)
			throws SortingPagingException;
	
	ExtendedPaginatedList getRecPageConfirmations(ExtendedPaginatedList paginatedList, String surname, String firstName, String date)
			throws SortingPagingException;

	ExtendedPaginatedList getRecPageDeaths(ExtendedPaginatedList paginatedList, String surname, String firstName, String date, String burialYear)
			throws SortingPagingException;
	
	
	Integer[] getBaptismYears();
	
	Integer[] getConfirmationYears();
	
	Integer[] getDeathYears();
	
	Integer[] getBurialYears();
	
	Integer[] getFianceesYears();
	
	List<Person> getPersons(String firstName, String surname);
	
	List<Person> getPersons();
	
	Person getPerson(long id);
	
	List<Person> getPersonsWithoutId(String firstName, String surname, Long id);
	
	List<Person> getAllWithoutId(Long id);
	
	Person savePerson(Person person) throws ObjectExistsException;
}
