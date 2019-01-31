package org.parafia.service;

import java.util.Date;

import org.parafia.model.Mail;
import org.parafia.model.MailFile;

public interface FileManager extends GenericManager<Mail, Long> {
	public String getNextMailNumber();
	
	public java.io.File[] getDirectoryList(String root);
	
	public Mail[] getFilesList(String root);
	
	Mail saveMail(Mail mail);
	
	public Mail[] getLastTenFilesList(Date date, String dir);
	
	public boolean removeDir(String fileName);
	
	public boolean removePhysicalFile(long id);
	
	public boolean removeMail(long id);
	
	public Mail get(String path);
	
	public MailFile getMailFile(long id);
	
	public boolean addSubDir(String dirName);
	
	public boolean addDir(String dir, String name);
	
	public boolean moveDir(String oldDir, String newPath);
	
	public boolean moveFile(String newPath, long id);
	
	public Mail[] findFiles(String text, String fromDate, String toDate);
}
