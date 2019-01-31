package org.parafia.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;
import org.parafia.dao.AddresseeDao;
import org.parafia.model.Addressee;
import org.parafia.service.AddresseeManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.service.exceptions.ObjectNotRemoved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service("addresseeManager")
public class AddresseeManagerImpl extends GenericManagerImpl<Addressee, Long> implements AddresseeManager
{
	@Autowired
	public AddresseeManagerImpl(AddresseeDao addresseeDao) {
		super(addresseeDao);
	}

	protected AddresseeDao getAddresseeDao()
	{
		return (AddresseeDao) genericDao;
	}

	public Addressee saveAddressee(Addressee addressee) throws ObjectExistsException
	{
		try
		{
			return getAddresseeDao().saveAddressee(addressee);
		}
		catch (DataIntegrityViolationException e)
		{
			e.printStackTrace();
			log.warn(e.getMessage());
			throw new ObjectExistsException("Addressee '" + addressee.getFullName() + "' already exists!");
		}
		catch (EntityExistsException e)
		{ // needed for JPA
			e.printStackTrace();
			log.warn(e.getMessage());
			throw new ObjectExistsException("Addressee '" + addressee.getFullName() + "' already exists!");
		}
	}

	public List<Character> getFirstLetters()
	{
		return getAddresseeDao().getFirstLetters();
	}

	public List<Addressee> getAddresseesWithFirstLetter(Character letter)
	{
		return getAddresseeDao().getAddresseesWithFirstLetter(letter);
	}

	public void removeAddressee(Long addresseeId) throws ObjectNotRemoved
	{
		try
		{
			getAddresseeDao().remove(addresseeId);
			log.error("Removing object");
		}
		catch (RollbackException ex)
		{
			log.error(ex);
			throw new ObjectNotRemoved("Could not remove addressee with id = " + addresseeId);
		}
	}
}
