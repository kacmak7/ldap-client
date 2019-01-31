package org.parafia.webapp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.service.FileManager;
import org.parafia.util.FileComparator;
import org.parafia.util.FileUtil;
import org.parafia.util.JSONBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MailListController extends BaseController {
	@Autowired
	private FileManager fileManager;
	@Value("${dir.parafiaFiles}")
	private String uploadDir;
	private final FileComparator fc = new FileComparator();
	private final Logger log = Logger.getLogger(getClass());
	@Autowired
	private MessageSource messageSource;
	
	public File[] getYearList() throws FileNotFoundException {
		//additional check block to verify path existence
		{
			File tmp = new File(uploadDir);
			if(!tmp.exists())
				if(!tmp.mkdirs()) //path creation if not existent
				{
					log.error(uploadDir + " not found, and creation failed. Some file structure changes may occured before error. \n"
							+ "Plese check: " + uploadDir + " path for changes in file structure and grant necessary privileges for app to create this path, or create it manually.");
					throw new FileNotFoundException();
				}
		}
		File[] yearList = FileUtil.getDirectoryList(uploadDir, false);
		if (yearList.length == 0) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			FileUtil.addSubDir(uploadDir, String.valueOf(year));
			yearList = FileUtil.getDirectoryList(uploadDir, false);
		} else if (yearList.length > 0) {
			java.util.Arrays.sort(yearList, fc);
		}
		
		return yearList;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/mail/viewMails")
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		File[] yearList = getYearList();
		
		String fileSeparator = Constants.FILE_SEP;
		log.debug("fileSeparator = " + fileSeparator);
		if (fileSeparator.equals("\\")) {
			fileSeparator = "\\\\";
		}
		
		String yearDir = null;
		String year = request.getParameter(Constants.YEAR); 
		
		if (year != null) {		//user kliknal na rok
			yearDir = uploadDir + Constants.FILE_SEP + request.getParameter(Constants.YEAR);
			request.getSession().setAttribute(Constants.YEAR, request.getParameter(Constants.YEAR));
		} else {
			if (request.getSession().getAttribute(Constants.YEAR) != null) {
				yearDir = uploadDir + Constants.FILE_SEP + request.getSession().getAttribute(Constants.YEAR);
				year = (String) request.getSession().getAttribute(Constants.YEAR);
			} else {
				yearDir = yearList[yearList.length - 1].getAbsolutePath();
				
				for (File file : yearList) {
					log.debug(file.toString());
				}
				log.debug("Stad bierzemy " + yearDir);
				
				year = yearList[yearList.length - 1].getName();
			}
		}
		
		yearDir = yearDir.replaceAll("\\\\", "/");
		
		//saveMessage(request, new MessageSourceAccessor(messageSource).getMessage("last.ten.documents"));
		
		mv.addObject("yearList", yearList)
			.addObject(Constants.FILE_SEPARATOR, fileSeparator)
			.addObject("beginJson", JSONBuilder.buildFirstJson(yearDir, request.getContextPath()))
			.addObject("yearDir", yearDir)
			.addObject("year", year);
		if (request.getParameter("lastten") != null)
			mv.addObject("lastten", true);
		return mv;
	}
}
