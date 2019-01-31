package org.parafia.dao;

import java.util.Date;

import org.parafia.model.Mail;
import org.parafia.model.MailFile;

public interface FileDao extends GenericDao<Mail, Long> {
	public String getLastMailNumber(String year);
	
	Mail saveMail(Mail mail);
	
	public Mail get(String path);
	
	public MailFile getMailFile(long id);
	
	void removeMail(Mail mail);
	
	public Mail[] getFilesList(String root);
	
	public Mail[] getLastTenFilesList(Date date, String dir);
	
	public Mail[] getFilesListWithSubDir(String root);
	
	public Mail[] findFiles(String text, Date fromDate, Date toDate);
}
