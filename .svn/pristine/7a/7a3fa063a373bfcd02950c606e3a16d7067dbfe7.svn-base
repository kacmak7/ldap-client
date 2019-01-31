package org.parafia.dao;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.parafia.model.Person;

public interface PersonDao extends GenericDao<Person, Long> {

	int getRecPageCountBaptismes(String surname, String firstName, String date);
	
	List<Person> getRecPageBaptismes(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String surname, String firstName, String date);
	
	int getRecPageCountConfirmations(String surname, String firstName, String date);
	
	List<Person> getRecPageConfirmations(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String surname, String firstName, String date);
	
	int getRecPageCountDeaths(String surname, String firstName, String date, String burialYear);
	
	List<Person> getRecPageDeaths(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String surname, String firstName, String date, String burialYear);
		
	int getMinBaptismYear();
	
	int getMinConfirmationYear();
	
	int getMinDeathYear();
	
	int getMinBurialYear();
	
	int getMinFianceesYear();
	
	List<Person> getPersons(String firstName, String surname);
	
	List<Person> getPersons();
	
	Person getPerson(long id);
	
	List<Person> getPersonsWithoutId(String firstName, String surname, Long id);
	
	List<Person> getAllWithoutId(Long id);
	
	Person savePerson(Person person);
}
