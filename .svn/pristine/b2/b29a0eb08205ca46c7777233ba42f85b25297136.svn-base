package org.parafia.service;

import java.util.List;

import org.parafia.model.Family;
import org.parafia.model.Person;
import org.parafia.model.dictionary.BaptismPlace;
import org.parafia.model.dictionary.MarriageStatus;
import org.parafia.model.dictionary.Practising;
import org.parafia.model.dictionary.TakePart;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.SortingPagingException;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;

public interface FamilyManager extends GenericManager<Family, Long> {
	List<Family> getFamilies();
	
	List<Family> getFamilies(String surname, String street, String firstNumber);
	
	ExtendedPaginatedList getRecPageFamilies(ExtendedPaginatedList paginatedList, String surname, String street, String firstNumber)
		throws SortingPagingException;
	
	Family saveFamily(Family family) throws ObjectExistsException;
	
	List<MarriageStatus> getMarriageStatuses();
	
	List<TakePart> getTakePart();
	
	Person savePerson(Person person) throws ObjectExistsException;
	
	List<Practising> getPractising();
	
	List<BaptismPlace> getBaptismPlaces();
}
