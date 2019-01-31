package org.parafia.dao;

import java.util.List;

import org.parafia.model.Columbarium;
import org.parafia.model.Grave;
import org.parafia.model.GraveFile;
import org.parafia.model.Niche;
import org.parafia.model.dictionary.GraveDoubled;
import org.parafia.model.dictionary.GraveGrounded;
import org.parafia.model.dictionary.GraveOwned;
import org.parafia.service.exceptions.MoreThanOneGeomException;

public interface GraveyardDao extends GenericDao<Grave, Long> {
	Grave getGrave(long graveId);
	Grave getGraveWithNumber(int number);
	Grave getGraveWithGeom(String postgisString) throws MoreThanOneGeomException;
	String getGeomForGrave(long graveId) throws MoreThanOneGeomException;
	
	Grave saveGrave(Grave grave);
	
	List<GraveDoubled> getGraveDoubled();
	List<GraveOwned> getGraveOwned();
	List<GraveGrounded> getGraveGrounded();
	
	GraveFile getFile(long fileId);
	
	List<String> getSectors();
	
	List<Grave> loadAll();
	List<Grave> loadAllWithFiles();
	List<Grave> loadAllFromSector(String sector);
	List<Grave> loadInvalid(int currentYear);
	
	List<Grave> findGraves(String graveOwnerFirstName, String graveOwnerSurname,
			String deadPersonFirstName, String deadPersonSurname, String graveNumber);
	
	List<Grave> findGravesWithPersons(String graveOwnerFirstName, String graveOwnerSurname,
			String deadPersonFirstName, String deadPersonSurname, String graveNumber);
	
	void executeCustomQuery(String sql);
	void deleteGrave(Grave grave);
	
	List<Columbarium> loadColumbariums();
	List<Niche> loadNiches();
	List<Niche> loadNiches(long columbariumId);
	List<Niche> loadNichesWithPersons(String ownerFirstName, String ownerSurname,
			String deadPersonFirstName, String deadPersonSurname, String number);
	Niche getNiche(long nicheId);
	Niche getNicheWithNumber(int number);
	Niche saveNiche(Niche niche);
}
