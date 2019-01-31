package org.parafia.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.parafia.Constants;
import org.parafia.dao.FileDao;
import org.parafia.model.Mail;
import org.parafia.model.MailFile;
import org.parafia.service.FileManager;
import org.parafia.util.DateUtil;
import org.parafia.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileManager")
public class FileManagerImpl extends GenericManagerImpl<Mail, Long> implements FileManager {
	@Autowired
	FileDao fileDao;
	
	public Mail saveMail(Mail mail) {
		return fileDao.saveMail(mail);
	}
	
	public Mail get(String path) {
		return fileDao.get(path);
	}
	
	public MailFile getMailFile(long id) {
		return fileDao.getMailFile(id);
	}
	
	/**
	 * @return next free number for new mail thread
	 */
	public String getNextMailNumber() {
		String currentYear = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR).format(new Date());
		String lastMailNumber = fileDao.getLastMailNumber(currentYear);
		if (lastMailNumber == null)
			return "1/" + currentYear;
		
		try {
			int number = Integer.valueOf(lastMailNumber.split("/")[0]);
			number++;
			return number + "/" + currentYear;
		} catch (NumberFormatException nfe) {
			log.error(nfe);
			return null;
		} catch (ArrayIndexOutOfBoundsException aex) {
			log.error(aex);
			return null;
		}
	}
	
	public java.io.File[] getDirectoryList(String root) {
		return FileUtil.getDirectoryList(root, false);
	}
	
	public Mail[] getFilesList(String root) {
		return fileDao.getFilesList(root);
	}
	
	public Mail[] getLastTenFilesList(Date date, String dir) {
		return fileDao.getLastTenFilesList(date, dir);
	}
	
	public boolean removeDir(String fileName) {
		if (fileName == null)
			return false;
		log.debug("Usuwam katalog " + fileName);
		Mail[] files = fileDao.getFilesListWithSubDir(fileName);
		for (Mail f : files) {
			//fileDao.remove(f.getId());
			removeMail(f.getId());
		}
		java.io.File f = new java.io.File(fileName);
		return FileUtil.removeFile(f);
	}
	
	public boolean removePhysicalFile(long id) {
		log.debug("Usuwam plik " + id);
		MailFile file = getMailFile(id);
		java.io.File f = new java.io.File(file.getPath());
		return FileUtil.removeFile(f);
	}
	
	public boolean removeMail(long id) {
		Mail mail = fileDao.get(id); 
		fileDao.removeMail(mail);
		for (MailFile file : mail.getFiles()) {
			java.io.File f = new java.io.File(file.getPath());
			FileUtil.removeFile(f);
		}
		
		return true;
	}
	
	public boolean addSubDir(String dirName) {
		return FileUtil.addSubDir(dirName);
	}
	
	public boolean addDir(String dir, String name) {
		return FileUtil.addDir(dir, name);
	}
	
	public boolean moveDir(String oldDir, String newPath) {
		Mail[] mails = fileDao.getFilesListWithSubDir(oldDir);
		for (Mail m : mails) {
			//for (MailFile f : m.getFiles())
			//	f.setPath(f.getPath().replace(oldDir, newPath));
			m.setPath(newPath);
			fileDao.save(m);
		}
		return FileUtil.moveDir(oldDir, newPath);
	}
	
	public boolean moveFile(String newPath, long id) {
		File f = new File(newPath);
		if (f.exists() && f.isDirectory()) {
			Mail mail = fileDao.get(id);
			mail.setPath(newPath);
			fileDao.save(mail);
			return true;
		} else
			return false;
	}
	
	public Mail[] findFiles(String text, String fromDate, String toDate) {
		Date fDate = null;
		Date tDate = null;
		try {
			fDate = DateUtil.convertStringToDate(fromDate);
		} catch (ParseException pe) {
			log.debug("fromDate " + fDate + " is invalid");
		}
		try {
			tDate = DateUtil.convertStringToDate(toDate);
		} catch (ParseException pe) {
			log.debug("toDate " + tDate + " is invalid");
		}
		return fileDao.findFiles(text, fDate, tDate);
	}
}
