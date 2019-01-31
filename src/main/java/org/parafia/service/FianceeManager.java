package org.parafia.service;

import org.parafia.model.FianceePair;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.SortingPagingException;
import org.parafia.webapp.util.pagination.ExtendedPaginatedList;

public interface FianceeManager extends GenericManager<FianceePair, Long> {
	FianceePair saveFianceePair(FianceePair fp) throws ObjectExistsException;
	
	FianceePair get(Long id);
	
	FianceePair getFianceePairForPerson(Long id);
	
	FianceePair getFianceePair(Long id);
	
	int getMaxProtocoleNumber(int year);
	
	ExtendedPaginatedList getRecPageFianceePairs(ExtendedPaginatedList paginatedList, String firstName, String surname, int year)
		throws SortingPagingException;
}
