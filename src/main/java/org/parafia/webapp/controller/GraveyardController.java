package org.parafia.webapp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.Grave;
import org.parafia.model.GraveFile;
import org.parafia.model.GraveLevel;
import org.parafia.model.GraveOwner;
import org.parafia.model.GravePerson;
import org.parafia.model.Messages;
import org.parafia.model.dictionary.GraveDoubled;
import org.parafia.model.dictionary.GraveGrounded;
import org.parafia.model.dictionary.GraveOwned;
import org.parafia.service.GraveyardManager;
import org.parafia.util.FileUtil;
import org.parafia.webapp.controller.commands.GraveFindFilter;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes(value = { Constants.GRAVE_FILTER, "successMessages", "errors" })
public class GraveyardController extends AbstractGraveyardHelper
{
	private Logger log = Logger.getLogger(getClass());

	@Autowired
	private GraveyardManager graveyardManager;
	@Autowired
	private BaseControllerTemp base;
	@Value("${dir.yardFiles}")
	private String uploadDir;
	private Locale locale = LocaleContextHolder.getLocale();

	@ModelAttribute(Constants.GRAVE_FILTER)
	public GraveFindFilter getGraveFilter()
	{
		return new GraveFindFilter();
	}

	@ModelAttribute("successMessages")
	public Messages<String> getMessages()
	{
		return new Messages<>();
	}

	@ModelAttribute("errors")
	public Errors<String> getErrors()
	{
		return new Errors<>();
	}

	@InitBinder
	protected void initBinder(@RequestParam(value = "graveId", required = false) Integer graveId,
			final WebDataBinder binder)
	{
		CustomPropertyEditor graveDoubledEditor = new CustomPropertyEditor(graveyardManager.getGraveDoubled());
		binder.registerCustomEditor(GraveDoubled.class, graveDoubledEditor);

		CustomPropertyEditor graveOwnedEditor = new CustomPropertyEditor(graveyardManager.getGraveOwned());
		binder.registerCustomEditor(GraveOwned.class, graveOwnedEditor);

		CustomPropertyEditor graveGroundedEditor = new CustomPropertyEditor(graveyardManager.getGraveGrounded());
		binder.registerCustomEditor(GraveGrounded.class, graveGroundedEditor);

		if (graveId != null)
		{
			log.debug("rejestrujemy filesEditor");

			CustomPropertyEditor filesEditor = new CustomPropertyEditor(graveyardManager.getGrave(graveId).getFiles());
			binder.registerCustomEditor(GraveFile.class, filesEditor);
		}
	}

	// -- TESTED -- //
	@RequestMapping(method = RequestMethod.GET, value = "/yard/yard")
	public ModelAndView drawYard()
	{
		return new ModelAndView("/yard/yard");
	}

	// -- TESTED -- //
	@RequestMapping(method = RequestMethod.GET, value = "/yard/yardFind")
	public ModelAndView yardFind()
	{
		return new ModelAndView("/yard/yardFind");
	}

	// -- TESTED -- //
	@RequestMapping(method = RequestMethod.POST, value = "/yard/yardFind")
	public ModelAndView onSubmit(
			@ModelAttribute(Constants.GRAVE_FILTER) GraveFindFilter filter,
			@ModelAttribute("errors") Errors<String> errors) throws Exception
	{
		List<Grave> foundedGraves = null;
		log.debug("entering 'onSubmit' method...");
		ModelAndView mv = new ModelAndView();
		
		try {
			foundedGraves = graveyardManager.findGraves(filter.getGraveOwnerFirstName(),
					filter.getGraveOwnerSurname(), filter.getDeadPersonFirstName(), filter.getDeadPersonSurname(),
					filter.getGraveNumber());
		}catch(NumberFormatException ex){
			errors.add(base.getText("errors.valid", locale));
			log.error(ex);
			mv.setViewName("/yard/yardFind");
			return mv;
		}

		List<Long> redGraves = new ArrayList<Long>();
		for (Grave grave : foundedGraves)
		{
			redGraves.add(grave.getId());
			log.debug(grave.getId());
		}
		mv.setViewName("/yard/yard");
		mv.addObject("redGraves", redGraves);
		return mv;
	}

	// -- TESTED -- //
	@RequestMapping(value = "/yard/yardFindList", method = RequestMethod.GET)
	protected ModelAndView formBackingObject(@ModelAttribute(Constants.GRAVE_FILTER) GraveFindFilter filter)
			throws Exception
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("gravesList",
				graveyardManager.findGravesWithPersons(filter.getGraveOwnerFirstName(), filter.getGraveOwnerSurname(),
						filter.getDeadPersonFirstName(), filter.getDeadPersonSurname(), filter.getGraveNumber()));
		mv.setViewName("/yard/yardFindList");
		return mv;
	}

	// -- TESTED -- //
	@RequestMapping(value = "/yard/yardFindList", method = RequestMethod.POST)
	public ModelAndView onSubmitList(
			@ModelAttribute(Constants.GRAVE_FILTER) GraveFindFilter filter,
			@ModelAttribute("errors") Errors<String> errors) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		ModelAndView mv = new ModelAndView("/yard/yardFindList");
		
		List<Grave> foundedGraves = null;
		try {
			foundedGraves = graveyardManager.findGravesWithPersons(filter.getGraveOwnerFirstName(),
					filter.getGraveOwnerSurname(), filter.getDeadPersonFirstName(), filter.getDeadPersonSurname(),
					filter.getGraveNumber());
		}catch(NumberFormatException ex){
			errors.add(base.getText("errors.valid", locale));
			log.error(ex);
			return mv;
		}
		mv.addObject("gravesList", foundedGraves);
		return mv;
	}

	// -- TESTED -- //
	@RequestMapping(value = "/yard/yardInvalid", method = RequestMethod.GET)
	public ModelAndView yardInwalid() throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("entering 'handleRequest' method...");
		}

		List<Grave> graves = null;

		ModelAndView mv = new ModelAndView("/yard/yardInvalid");

		int currentYear = Integer.valueOf(new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR).format(new Date()));
		graves = graveyardManager.loadInvalid(currentYear);
		mv.addObject("graves", graves);
		return mv;

	}

	// -- TESTED -- //
	@RequestMapping(value = "/yard/graveDetails", method = RequestMethod.GET)
	protected ModelAndView graveDetails(@RequestParam(value = "graveId", required = false) Long graveId,
			@RequestParam(value = "graveNumber", required = false) Integer graveNumber,
			@RequestParam(value = "sectorx", required = false) Object sectorx) throws Exception
	{
		ModelAndView mv = new ModelAndView("/yard/graveDetails");
		Grave grave;

		if (graveId != null)
			grave = graveyardManager.getGrave(graveId);
		else
			grave = graveyardManager.getGraveWithNumber(graveNumber, false);

		mv.addObject(Constants.PHOTO_URL, uploadDir + "/"); // nie uzywamy FileSeparatora, bo sie krzaczy

		if (grave.getOwner() == null)
			grave.setOwner(new GraveOwner());

		mv.addObject(Constants.GRAVE_DOUBLED_LIST, graveyardManager.getGraveDoubled());
		mv.addObject(Constants.GRAVE_OWNED_LIST, graveyardManager.getGraveOwned());
		mv.addObject(Constants.GRAVE_GROUNDED_LIST, graveyardManager.getGraveGrounded());

		mv.addObject("uploadDir", uploadDir);

		if (sectorx != null)
			mv.addObject("sectorx", sectorx);

		mv.addObject("grave", grave);

		return mv;
	}

	// -- TESTED -- //
	@RequestMapping(value = "/yard/graveDetails", method = RequestMethod.POST)
	public ModelAndView onSubmit(
			@ModelAttribute("grave") Grave grave, 
			@RequestParam(value = "fileData", required = false) MultipartFile[] files,
			@ModelAttribute("errors") Errors<String> errors,
			@RequestParam(value = "deletedFilesIds", required = false) String deletedFilesIds,
			@ModelAttribute("successMessages") Messages<String> msg
			) throws Exception
	{
		ModelAndView mv = new ModelAndView("/yard/graveDetails");

		log.debug("entering 'onSubmit' method...");
		
		ArrayList<GraveFile> filesList = new ArrayList<>();
		for(GraveFile file : grave.getFiles()) {
			Long fileId = file.getId();
			filesList.add(graveyardManager.getFile(fileId));
		}
		
		grave.setFiles(filesList);

		for (GravePerson person : grave.getPersons())
			person.setGrave(grave);

		for (GraveLevel level : grave.getLevels())
			level.setGrave(grave);

		if (files != null)
			for (MultipartFile fl : files)
			{
				CommonsMultipartFile f = (CommonsMultipartFile) fl;

				if (f.getSize() > 0)
				{
					GraveFile file = new GraveFile();
					file.setContentType(f.getContentType());
					file.setOriginalName(f.getOriginalFilename().replaceAll("'", ""));
					file.setGrave(grave);
					file.setSize(f.getSize());
					if (!grave.getFiles().contains(file))
					{
						grave.getFiles().add(file);

						InputStream stream = new BufferedInputStream(f.getInputStream());

						try
						{
							file.setPath(saveFile(stream, uploadDir, String.valueOf(grave.getId())));
							if (file.getPath() == null)
							{
								errors.add("errors.existing.grave-photo");
								return mv;
							}
							log.debug("Plik zapisano do sciezki " + file.getPath());
						}
						catch (IOException ioex)
						{
							log.error(ioex);
							return mv;
						}
						finally
						{
							stream.close();
						}
					}
				}
			}

		for (GraveFile file : grave.getFiles())
		{
			file.setGrave(grave);
		}
		
		processFiles(deletedFilesIds, grave);

		try
		{
			graveyardManager.premergeSave(grave);
			msg.add(base.getText("grave.saved", locale));
			mv.setView(new RedirectView("/yard/yardFindList.html", true));
			return mv;
		}
		catch (Exception e)
		{
			errors.add(base.getText("errors.existing.grave", locale));
			return mv;
		}
	}

	private String saveFile(InputStream stream, String upDir, String fileName) throws IOException
	{
		String fakeFileName = UUID.randomUUID().toString();
		String path = upDir + Constants.FILE_SEP + fakeFileName;

		File f = new File(path);
		if (f.exists())
		{
			FileUtil.removeFile(f);
			log.fatal("Zostal wytworzony nieunikalny UUID! " + fakeFileName);
		}

		OutputStream bos = new FileOutputStream(path);
		log.debug("Stream bytes available " + stream.available());
		int bytesRead;
		byte[] buffer = new byte[8192];

		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1)
		{
			bos.write(buffer, 0, bytesRead);
		}
		bos.close();
		return fakeFileName;
	}

	private void processFiles(String deletedFilesIds, Grave grave)
	{
		if (deletedFilesIds != null && !"".equals(deletedFilesIds))
		{
			String[] deletedFiles = deletedFilesIds.split("_");
			for (String fileId : deletedFiles)
			{
				if (!"".equals(fileId))
				{
					GraveFile file = graveyardManager.getFile(Long.parseLong(fileId));
					graveyardManager.deletePhysicalFile(uploadDir + Constants.FILE_SEP + file.getPath());
					grave.getFiles().remove(file);
				}
			}
		}
	}
}
