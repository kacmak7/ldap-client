package org.parafia.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.parafia.Constants;
import org.parafia.dao.GenericDao;
import org.parafia.dao.GraveyardDao;
import org.parafia.model.Columbarium;
import org.parafia.model.Grave;
import org.parafia.model.GraveFile;
import org.parafia.model.Niche;
import org.parafia.model.dictionary.GraveDoubled;
import org.parafia.model.dictionary.GraveGrounded;
import org.parafia.model.dictionary.GraveOwned;
import org.parafia.service.GraveyardManager;
import org.parafia.service.exceptions.MoreThanOneGeomException;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service("graveyardManager")
public class GraveyardManagerImpl extends GenericManagerImpl<Grave, Long> implements GraveyardManager {
	@Autowired
	GraveyardDao graveyardDao;
	
	@Autowired
	public GraveyardManagerImpl(@Qualifier("graveyardDao") GenericDao<Grave, Long> d) 
	{
		super.genericDao = d;
	}
	
	public List<GraveDoubled> getGraveDoubled() {
		return graveyardDao.getGraveDoubled();
	}
	
	public List<GraveOwned> getGraveOwned() {
		return graveyardDao.getGraveOwned();
	}
	
	public List<GraveGrounded> getGraveGrounded() {
		return graveyardDao.getGraveGrounded();
	}
	
	public Grave getGrave(long graveId) {
		return graveyardDao.getGrave(graveId);
	}
	
	public Grave getGraveWithGeom(String postgisString) throws MoreThanOneGeomException {
		return graveyardDao.getGraveWithGeom(postgisString);
	}
	
	public Grave getGraveWithNumber(int number, boolean withGeom) {
		Grave grave = graveyardDao.getGraveWithNumber(number);
		if (withGeom == true && grave != null) {
			try {
				grave.setPostgisString(graveyardDao.getGeomForGrave(grave.getId()));
			} catch (MoreThanOneGeomException ex) {
				log.fatal("There is more than one GEOM for the grave " + grave.getNumber());
				ex.printStackTrace();
				return null;
			}
		}
		return grave;
	}
	
	
	
	public GraveFile getFile(long fileId) {
		return graveyardDao.getFile(fileId);
	}
	
	public Grave saveGrave(Grave grave) throws ObjectExistsException {
		try {
			Grave g = graveyardDao.saveGrave(grave);
			String postGis = grave.getPostgisString();
			if (postGis != null) {		//tylko przy inicjalizacji z pliku DXF
				String sql = "update graves set the_geom=ST_GeomFromText('" + postGis + "') where id="+g.getId()+";";
				log.debug(sql);
				graveyardDao.executeCustomQuery(sql);
			}
			
			return g;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Grave '" + grave.getOwner().getSurname() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Grave '" + grave.getOwner().getSurname() + "' already exists!");
        }
	}
	
	public List<Grave> loadInvalid(int currentYear) {
		return graveyardDao.loadInvalid(currentYear);
	}
	
	public List<Grave> loadAll() {
		return graveyardDao.loadAll();
	}
	
	public List<Grave> loadAllWithFiles() {
		return graveyardDao.loadAllWithFiles();
	}
	
	public List<Grave> loadAllFromSector(String sector) {
		return graveyardDao.loadAllFromSector(sector);
	}
	
	public List<String> getSectors() {
		return graveyardDao.getSectors();
	}

	@Deprecated
	public boolean removePhoto(String photoUrl, Long graveId) {
		Grave grave = graveyardDao.get(graveId);
		graveyardDao.save(grave);
		return FileUtil.removeFile(photoUrl + Constants.FILE_SEP + graveId);
	}
	
	public boolean deletePhysicalFile(String path) {
		return FileUtil.removeFile(path);
	}

	public List<Grave> findGraves(String graveOwnerFirstName,
			String graveOwnerSurname, String deadPersonFirstName,
			String deadPersonSurname, String graveNumber) {
		return graveyardDao.findGraves(graveOwnerFirstName, graveOwnerSurname, deadPersonFirstName, deadPersonSurname, graveNumber);
	}
	
	public List<Grave> findGravesWithPersons(String graveOwnerFirstName,
			String graveOwnerSurname, String deadPersonFirstName,
			String deadPersonSurname, String graveNumber) {
		return graveyardDao.findGravesWithPersons(graveOwnerFirstName, graveOwnerSurname, deadPersonFirstName, deadPersonSurname, graveNumber);
	}
	
	public void deleteGrave(Long graveId) {
		graveyardDao.deleteGrave(graveyardDao.get(graveId));
	}
	
	public List<Niche> loadNiches(Columbarium columbarium) {
		return graveyardDao.loadNiches(columbarium.getId());
	}
	
	public List<Niche> loadNichesWithPersons(String ownerFirstName, String ownerSurname,
			String deadPersonFirstName, String deadPersonSurname, String number) {
		return graveyardDao.loadNichesWithPersons(ownerFirstName, ownerSurname, deadPersonFirstName, deadPersonSurname, number);
	}
	
	@Override
	public List<Columbarium> loadColumbariums() {
		return graveyardDao.loadColumbariums();
	}
	
	@Override
	public Niche getNiche(long nicheId) {
		return graveyardDao.getNiche(nicheId);
	}
	
	@Override
	public Niche getNicheWithNumber(int number) {
		return graveyardDao.getNicheWithNumber(number);
	}
	
	public Niche saveNiche(Niche niche) throws ObjectExistsException {
		try {
			return graveyardDao.saveNiche(niche);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Niche '" + niche.getOwner().getSurname() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ObjectExistsException("Niche '" + niche.getOwner().getSurname() + "' already exists!");
        }
	}

	@Override
	public List<Niche> loadNiches()
	{
		return graveyardDao.loadNiches();
	}
}