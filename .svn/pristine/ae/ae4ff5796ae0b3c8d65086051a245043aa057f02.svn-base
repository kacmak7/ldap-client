package org.parafia.webapp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Columbarium;
import org.parafia.model.Errors;
import org.parafia.model.GraveFile;
import org.parafia.model.GraveOwner;
import org.parafia.model.GravePerson;
import org.parafia.model.Messages;
import org.parafia.model.Niche;
import org.parafia.model.dictionary.GraveOwned;
import org.parafia.service.GraveyardManager;
import org.parafia.util.FileUtil;
import org.parafia.webapp.controller.commands.NicheFindFilter;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@SessionAttributes(value = { "successMessages", "errors", Constants.NICHE_FILTER })
public class ColumbariumController extends BaseFormController
{
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private GraveyardManager graveyardManager;
	@Autowired
	private BaseControllerTemp base;
	@Value("${dir.yardFiles}")
	private String uploadDir;

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

	@ModelAttribute("niche")
	public Niche getNiche()
	{
		return new Niche();
	}

	@ModelAttribute(Constants.NICHE_FILTER)
	public NicheFindFilter getFilter()
	{
		return new NicheFindFilter();
	}

	@ModelAttribute("columbariums")
	public List<Columbarium> getColumbariums()
	{
		return new ArrayList<>();
	}

	@InitBinder
	protected void initBinder(final WebDataBinder binder,
			@RequestParam(value = "nicheId", required = false) String nicheId)
	{
		CustomPropertyEditor graveOwnedEditor = new CustomPropertyEditor(graveyardManager.getGraveOwned());
		binder.registerCustomEditor(GraveOwned.class, graveOwnedEditor);

		CustomPropertyEditor nicheEditor = new CustomPropertyEditor(graveyardManager.loadColumbariums());
		binder.registerCustomEditor(Niche.class, nicheEditor);

		if (nicheId != null)
		{
			log.debug("rejestrujemy filesEditor");
			CustomPropertyEditor filesEditor = new CustomPropertyEditor(
					graveyardManager.getNiche(Integer.parseInt(nicheId)).getFiles());
			binder.registerCustomEditor(GraveFile.class, filesEditor);
		}
	}

	// -- TESTED -- //
	@RequestMapping(value = "/columbarium/columbarium")
	public ModelAndView handleRequest(@RequestParam(value = "niche", required = false) String nicheId,
			@RequestParam(value = "columbarium", required = false) String columbariumName) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("entering 'handleRequest' method...");
		}

		ModelAndView returnModel = null;

		if (nicheId != null)
		{
			// request.setAttribute("nicheId", nicheId);
			RedirectView rv = new RedirectView("columbarium/nicheDetails.html");
			returnModel = new ModelAndView(rv, "nicheNumber", nicheId);
		}
		else
		{
			List<Columbarium> columbariums = graveyardManager.loadColumbariums();
			List<Niche> niches = null;
			if (columbariums.size() > 0)
			{
				Columbarium activeColumbarium = null;
				if (columbariumName != null)
				{
					for (Columbarium col : columbariums)
					{
						if (col.getName().equals(columbariumName))
						{
							activeColumbarium = col;
						}
					}
				}
				if (activeColumbarium == null)
					activeColumbarium = columbariums.get(0);

				niches = graveyardManager.loadNiches(activeColumbarium);
			}

			returnModel = new ModelAndView("columbarium/columbarium");
			returnModel.addObject("columbariums", columbariums);
			if (niches != null && niches.size() > 0)
			{
				List<List<Niche>> nichesGroups = new ArrayList<List<Niche>>();
				nichesGroups.add(new ArrayList<Niche>());
				for (Niche niche : niches)
				{
					List<Niche> currentGroup = nichesGroups.get(nichesGroups.size() - 1);
					if (currentGroup.size() > 20)
					{
						nichesGroups.add(new ArrayList<Niche>());
						currentGroup = nichesGroups.get(nichesGroups.size() - 1);
					}
					currentGroup.add(niche);
				}

				// request.setAttribute("niches", niches);
				returnModel.addObject("nichesGroups", nichesGroups);
			}
		}

		return returnModel;
	}

	// -- TESTED -- //
	@RequestMapping(value = "/columbarium/nicheDetails", method = RequestMethod.GET)
	public ModelAndView showForm(@ModelAttribute(value = "niche") Niche niche,
			@RequestParam(value = "nicheId", required = false) String nicheId,
			@RequestParam(value = "nicheNumber", required = false) String nicheNumber) throws Exception
	{
		ModelAndView mv = new ModelAndView();

		if (nicheId != null)
		{
			niche = graveyardManager.getNiche(Long.valueOf(nicheId));
		}
		else
			niche = graveyardManager.getNicheWithNumber(Integer.valueOf(nicheNumber));

		mv.addObject(Constants.PHOTO_URL, uploadDir + "/"); // nie uzywamy FileSeparatora, bo sie krzaczy

		if (niche.getOwner() == null)
			niche.setOwner(new GraveOwner());

		mv.addObject(Constants.GRAVE_OWNED_LIST, graveyardManager.getGraveOwned());

		mv.addObject("uploadDir", uploadDir);
		mv.addObject("niche", niche);
		mv.setViewName("columbarium/nicheDetails");
		return mv;
	}

	@RequestMapping(value = "columbarium/submitNiche", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("errors") List<String> errMsg,
			@ModelAttribute("successMessages") List<String> msg, 
			@ModelAttribute("niche") Niche niche,
			@RequestParam(value = "deletedFilesIds", required = false) String deletedFilesIds,
			@RequestParam(value = "fileData", required = false) MultipartFile[] files ,
			BindingResult errors) throws Exception
	{
		log.debug("entering 'onSubmit' method...");

		ArrayList<GraveFile> filesList = new ArrayList<>();
		for(GraveFile file : niche.getFiles()) {
			Long fileId = file.getId();
			filesList.add(graveyardManager.getFile(fileId));
		}
		niche.setFiles(filesList);
		
		for (GravePerson person : niche.getPersons())
		{
			person.setGrave(niche);
		}
		
		if (files != null)
			for (MultipartFile fi : files)
			{
				CommonsMultipartFile f = (CommonsMultipartFile) fi;

				if (f.getSize() > 0)
				{
					GraveFile file = new GraveFile();
					file.setContentType(f.getContentType());
					file.setOriginalName(f.getOriginalFilename().replaceAll("'", ""));
					file.setGrave(niche);
					file.setSize(f.getSize());
					if (!niche.getFiles().contains(file))
					{
						niche.getFiles().add(file);
						InputStream stream = new BufferedInputStream(f.getInputStream());

						try
						{
							file.setPath(saveFile(stream, uploadDir, String.valueOf(niche.getId())));
							if (file.getPath() == null)
							{
								errMsg.add("errors.existing.grave-photo");
								return new ModelAndView("columbarium/nicheDetails");
							}
							log.debug("Plik zapisano do sciezki " + file.getPath());
						}
						catch (IOException ioex)
						{
							// errors with saving the file
							errors.rejectValue("file", "errors.uploaddocument-fileexists");
							log.error(ioex);
							return new ModelAndView("columbarium/nicheDetails");
						}
						finally
						{
							stream.close();
						}
					}
				}
			}

		for (GraveFile file : niche.getFiles())
		{
			file.setGrave(niche);
		}
		
		processFiles(deletedFilesIds, niche);
		
		niche = graveyardManager.saveNiche(niche);
		Locale locale = LocaleContextHolder.getLocale();
		msg.add(base.getText("niche.saved", locale));
		ModelAndView ret = new ModelAndView(new RedirectView("columbarium.html"));
		return ret;
	}

	// -- TESTED -- //
	@RequestMapping(method = RequestMethod.GET, value = "/columbarium/findList")
	protected ModelAndView showList(@ModelAttribute(Constants.NICHE_FILTER) NicheFindFilter filter) throws Exception
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("columbarium/findList");
		mv.addObject("nichesList",
				graveyardManager.loadNichesWithPersons(filter.getOwnerFirstName(), filter.getOwnerSurname(),
						filter.getDeadPersonFirstName(), filter.getDeadPersonSurname(), filter.getNumber()));
		return mv;
	}

	// -- TESTED -- //
	@RequestMapping(value = "/columbarium/submitList", method = RequestMethod.POST)
	public ModelAndView onSubmit(
			@ModelAttribute(Constants.NICHE_FILTER) NicheFindFilter filter,
			@ModelAttribute("errors") List<String> errors) throws Exception
	{
		log.debug("entering 'onSubmit' method...");
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView mv = new ModelAndView("columbarium/findList");
		List<Niche> foundedNiches = null;
		
		try {
			foundedNiches = graveyardManager.loadNichesWithPersons(filter.getOwnerFirstName(), filter.getOwnerSurname(),
					filter.getDeadPersonFirstName(), filter.getDeadPersonSurname(), filter.getNumber());
		}catch(NumberFormatException ex){
			errors.add(base.getText("errors.valid", locale));
			log.error(ex);
			return mv;
		}
		mv.addObject("nichesList", foundedNiches);
		return mv;
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

	private void processFiles(String deletedFilesIds, Niche niche)
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
					niche.getFiles().remove(file);
				}
			}
		}
	}
}
