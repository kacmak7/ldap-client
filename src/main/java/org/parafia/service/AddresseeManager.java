package org.parafia.service;

import java.util.List;

import org.parafia.model.Addressee;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.ObjectNotRemoved;

public interface AddresseeManager extends GenericManager<Addressee, Long> {
	Addressee saveAddressee(Addressee addressee) throws ObjectExistsException;
	
	List<Character> getFirstLetters();
	
	List<Addressee> getAddresseesWithFirstLetter(Character letter);
	
	void removeAddressee(Long addresseeId) throws ObjectNotRemoved;
}
