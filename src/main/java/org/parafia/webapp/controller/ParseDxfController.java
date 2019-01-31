package org.parafia.webapp.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.parafia.model.Grave;
import org.parafia.model.dictionary.GraveDoubled;
import org.parafia.model.dictionary.GraveGrounded;
import org.parafia.model.dictionary.GraveOwned;
import org.parafia.service.GraveyardManager;
import org.parafia.util.DxfParser;
import org.parafia.webapp.controller.commands.DxfFile;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class ParseDxfController extends BaseFormController {
	private GraveyardManager graveyardManager;
	
	public ParseDxfController() {
        setCommandName("dxffile");
        setCommandClass(DxfFile.class);
        setSessionForm(true);
    }
	
	public void setGraveyardManager(GraveyardManager graveyardManager) {
		this.graveyardManager = graveyardManager;
	}

	@Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	return new DxfFile();
    }
	
	
	@Override
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command,
            BindException errors)
			throws Exception {
		DxfFile file = (DxfFile) command;
		
		// validate a file was entered
		if (file.getFile().length == 0) {
			Object[] args = new Object[] { getText("mail.file", request.getLocale()) };
			errors.rejectValue("file", "errors.required", args, "File");
			return showForm(request, response, errors);
		}
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile f = (CommonsMultipartFile) multipartRequest.getFile("file");
		
		InputStream stream = new BufferedInputStream(f.getInputStream());
		try {
        	DxfParser parser = new DxfParser(stream, "UTF-8");
        	List<Grave> graves = parser.getGraves();
        	//List<Grave> existingGraves = graveyardManager.loadAllWithText();
        	
        	List<GraveDoubled> doubled = graveyardManager.getGraveDoubled();
        	List<GraveGrounded> grounded = graveyardManager.getGraveGrounded();
        	List<GraveOwned> owned = graveyardManager.getGraveOwned();
        	
        	/*Set<Grave> myGraves = new HashSet<Grave>();
        	
        	for (Grave grave : graves) {
        		if (grave.getNumber() == 4916) {
        			log.error("mamy grob 4916");
        			log.error(grave.getPostgisString());
        			Grave existingGraveWithSameGeom = graveyardManager.getGraveWithGeom(grave.getPostgisString());
            		if (existingGraveWithSameGeom != null) {
            			log.debug("Same geom founded for grave " + grave.getNumber() + ". Grave will not be updated.");
            		} else {
            			log.info("Grave " + grave.getNumber() + " not found on an existing map! The new one contains: " + grave.getPostgisString());
        				Grave existingGraveWithSameNumber = graveyardManager.getGraveWithNumber(grave.getNumber(), true);
        				if (existingGraveWithSameNumber != null) {
        					log.info("Grave with same number found. The geometry of this grave is " + existingGraveWithSameNumber.getPostgisString());
        				}
            		}
            		myGraves.add(grave);
        		}
        	}*/
        	for (Grave grave : graves) {
        		Grave existingGraveWithSameGeom = graveyardManager.getGraveWithGeom(grave.getPostgisString());
        		if (existingGraveWithSameGeom != null) {
        			log.debug("Same geom founded for grave " + grave.getNumber() + ". Grave will not be updated.");
        		} else {
        			log.info("Grave " + grave.getNumber() + " not found on an existing map! The new one contains: " + grave.getPostgisString());
        			if (grave.getNumber() != 0) {
        				Grave existingGraveWithSameNumber = graveyardManager.getGraveWithNumber(grave.getNumber(), true);
        				if (existingGraveWithSameNumber != null) {
        					log.info("Grave with same number found. The geometry of this grave is " + existingGraveWithSameNumber.getPostgisString());
        					updateGraveGeometry(existingGraveWithSameNumber, grave.getPostgisString());        					
        				} else {
        					log.warn("Grave " + grave.getNumber() + " not found and there is no such a number in a database. The grave location is described as " + grave.getPostgisString() + ". The grave is probably new and we should add it.");
        					addNewGrave(grave);
        				}
        			} else {
        				log.warn("Grave " + grave.getNumber() + " not found. The grave location is described as " + grave.getPostgisString() + ". We will not add this grave.");
        			}
        		}
        	}
        	
        	log.info("Graves updated successfully");
        } finally {
        	stream.close();
        }
        
        saveMessage(request, "Plik " + getText("graves.was-added", request.getLocale()));
				
		return new ModelAndView(getSuccessView());
	}
	
	public void updateGraveGeometry(Grave grave, String geometry) {
		grave.setPostgisString(geometry);
		try {
			graveyardManager.saveGrave(grave);
		} catch (Exception e) {
			log.error("Grave was not updated!", e);
			return;
		}
	}
	
	public void addNewGrave(Grave grave) {
    	List<GraveDoubled> doubled = graveyardManager.getGraveDoubled();
    	List<GraveGrounded> grounded = graveyardManager.getGraveGrounded();
    	List<GraveOwned> owned = graveyardManager.getGraveOwned();
    	
		grave.setDoubled(doubled.get(0));
		grave.setGrounded(grounded.get(0));
		grave.setOwned(owned.get(0));
		try {
			if (grave.getSector() == null)
				grave.setSector("0");
			graveyardManager.saveGrave(grave);
			//graveyardManager.ex
		} catch (Exception e) {
			log.error("Grave was not saved!", e);
			return;
		}
	}
}
