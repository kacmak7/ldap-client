package org.parafia.dao;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.parafia.model.Family;
import org.parafia.model.Person;
import org.parafia.model.dictionary.BaptismPlace;
import org.parafia.model.dictionary.MarriageStatus;
import org.parafia.model.dictionary.Practising;
import org.parafia.model.dictionary.TakePart;
import org.parafia.service.exceptions.ObjectExistsException;

public interface FamilyDao extends GenericDao<Family, Long> {
	List<Family> getFamilies();
	
	List<Family> getFamilies(String surname, String street, String firstNumber);
	
	//int getRecPageCountFamilies();
	
	int getRecPageCountFamilies(String surname, String street, String firstNumber);
	
	//List<Family> getRecPageFamilies(int firstResult, int maxResults,
	//		SortOrderEnum sortDirection, String sortCriterion);
	
	List<Family> getRecPageFamilies(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String surname, String street, String firstNumber);
	
	List<MarriageStatus> getMarriageStatuses();
	
	Family saveFamily(Family family);
	
	List<TakePart> getTakePart();
	
	Person savePerson(Person person) throws ObjectExistsException;
	
	List<Practising> getPractising();
	
	List<BaptismPlace> getBaptismPlaces();
}
