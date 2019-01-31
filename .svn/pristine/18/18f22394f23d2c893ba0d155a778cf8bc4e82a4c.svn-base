package org.parafia.dao;

import java.util.List;

import org.parafia.model.Addressee;

public interface AddresseeDao extends GenericDao<Addressee, Long> {
	Addressee saveAddressee(Addressee addressee);
	
	List<Character> getFirstLetters();
	
	List<Addressee> getAddresseesWithFirstLetter(Character letter);
}
