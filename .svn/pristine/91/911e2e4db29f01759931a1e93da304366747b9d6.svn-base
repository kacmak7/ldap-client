package org.parafia.dao;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.parafia.model.FianceePair;

public interface FianceeDao extends GenericDao<FianceePair, Long> {
	FianceePair saveFianceePair(FianceePair fp);
	
	FianceePair getFianceePairForPerson(Long id);
	
	FianceePair getFianceePair(Long id);
	
	int getMaxProtocoleNumber(int year);

	int getRecPageCountFianceePairs(String firstName, String surname, int year);
	
	List<FianceePair> getRecPageFianceePairs(int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion, String firstName, String surname, int year);
}
