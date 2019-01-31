package org.parafia.webapp.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.parafia.Constants;
import org.parafia.model.Addressee;
import org.parafia.model.Grave;
import org.parafia.model.GraveFile;
import org.parafia.model.Mail;
import org.parafia.model.MailFile;
import org.parafia.service.AddresseeManager;
import org.parafia.service.FileManager;
import org.parafia.util.FileComparator;
import org.parafia.util.FileUtil;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Controller class to upload Files.
 *
 * <p>
 * <a href="FileUploadFormController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class FileUploadController extends BaseFormController {
	private FileManager fileManager;
	private AddresseeManager addresseeManager;
	private Logger log = Logger.getLogger(FileUploadController.class);
	private final FileComparator fc = new FileComparator();
	
	private String uploadDir;
	
	public void setFileManager(FileManager fileManager) {
	    this.fileManager = fileManager; 
	}
	
	public void setAddresseeManager(AddresseeManager addresseeManager) {
		this.addresseeManager = addresseeManager;
	}

	/**
	 * Sets the directory where files will be uploaded to
	 * @param uploadDir
	 */
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public FileUploadController() {
        setCommandName("mail");
        setCommandClass(Mail.class);
        //setSessionForm(true);
    }
	
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		CustomPropertyEditor addresseeEditor = new CustomPropertyEditor(addresseeManager.getAll());
		binder.registerCustomEditor(Addressee.class, addresseeEditor);
		super.initBinder(request, binder);
		SimpleDateFormat dateFormat = 
            new SimpleDateFormat(getText("datetime.format", request.getLocale()));
		dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, 
        		new CustomDateEditor(dateFormat, true));
	}
	
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	Mail mail = null;
    	if (request.getParameter("mailId") != null) {
    		mail = fileManager.get(Long.valueOf(request.getParameter("mailId")));
    	} else {
	    	mail = new Mail();
	        mail.setNumber(String.valueOf(fileManager.getNextMailNumber()));
	        
	        if (!FileUtil.directoryExists(request.getParameter("parentDir"))) {
	        	File[] yearList = FileUtil.getDirectoryList(uploadDir, false);
	    		java.util.Arrays.sort(yearList, fc);
	    		String path;
	    		if (yearList.length > 0)
	    			path = yearList[yearList.length - 1].getAbsolutePath();
	    		else
	    			path = uploadDir;
	    		mail.setPath(path);
	    		//saveMessage(request, getText("errors.directorynotexists", new Object[] {request.getParameter("parentDir"), path}, request.getLocale()));
	        } else
	        	mail.setPath(request.getParameter("parentDir"));
    	}
    	
    	request.setAttribute("letters", addresseeManager.getFirstLetters());
    	
        return mail;
    }
    
    @Override
    public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getCancelView());
        }

        return super.processFormSubmission(request, response, command, errors);
    }

	@Override
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command,
            BindException errors)
	throws Exception {
		Mail mail = (Mail) command;
		
		// the directory to upload to
		String upDir = mail.getPath();
		log.debug("The file will be thrown into a directory " + upDir);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    Iterator<String> it = multipartRequest.getFileNames();
	    //uploadDir += mail.getPath();
	    while (it.hasNext()) {
	    	String fileName = it.next();
	    	
	    	CommonsMultipartFile f = (CommonsMultipartFile) multipartRequest.getFile(fileName);
	    	
	    	if (f.getSize() > 0) {
		    	MailFile file = new MailFile();
		    	file.setContentType(f.getContentType());
		    	file.setOriginalName(f.getOriginalFilename().replaceAll("'", ""));
		    	file.setMail(mail);
		    	file.setSize(f.getSize());
		    	if (!mail.getFiles().contains(file)) {
		    		mail.getFiles().add(file);
		    		
		    		InputStream stream = new BufferedInputStream(f.getInputStream());
		    		
			        try {
			        	file.setName(uploadFile(request, stream, upDir, String.valueOf(mail.getId())));
			        	if (file.getName() == null) {
			        		saveError(request, "errors.existing.grave-photo");        		
			            	return showForm(request, response, errors);
			        	}
			        	log.debug("Plik zapisano pod nazwa " + file.getName());
			        	
			        	addPhrase(request, upDir, file.getName(), mail.getNumber());
			        } catch (IOException ioex) {	//errors with saving the file
			        	errors.rejectValue("file", "errors.uploaddocument-fileexists");
			        	log.error(ioex);
			        	return showForm(request, response, errors);
			        } finally {
			        	stream.close();
			        }
		    	}
	    	}
		}

        if (mail.getRegisterDate() == null)
			mail.setRegisterDate(new Date());
		
        processFiles(request, mail);
		
		fileManager.saveMail(mail);
		
		saveMessage(request, getText("mail.list.was-added", request.getLocale()));
		return new ModelAndView(getSuccessView());
	}    
    
	/*private boolean saveFile(HttpServletRequest request, InputStream stream, String upDir, String fileName) throws IOException {
    	File f = new File(upDir + Constants.FILE_SEP + fileName);
    	if (!f.exists()) {
			OutputStream bos = new FileOutputStream(upDir + Constants.FILE_SEP + fileName);
			logger.debug("Stream bytes available " + stream.available());
	        int bytesRead;
	        byte[] buffer = new byte[8192];
	
	        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
	            bos.write(buffer, 0, bytesRead);
	        }
	        bos.close();
	        return true;
    	} else {
    		return false;
    	}
    }*/
	
	private String uploadFile(HttpServletRequest request, InputStream stream, String upDir,
    		String fileName) throws IOException {
    	String fakeFileName = UUID.randomUUID().toString();
    	String path = upDir + Constants.FILE_SEP + fakeFileName;
    	
    	File f = new File(path);
    	if (f.exists()) {
    		FileUtil.removeFile(f);
    		log.fatal("Zostal wytworzony nieunikalny UUID! " + fakeFileName);
    	}
    		
		OutputStream bos = new FileOutputStream(path);
		logger.debug("Stream bytes available " + stream.available());
        int bytesRead;
        byte[] buffer = new byte[8192];

        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();
        return fakeFileName;
    }
    
    private void addPhrase(HttpServletRequest request, String dir, String name, String number) {
    	String path = dir + Constants.FILE_SEP + name;
    	
    	InputStream stream = null;
    	OutputStream out = null;
    	try {
    		//stream = new FileInputStream(path);
	    	//HWPFDocument doc = new HWPFDocument(stream);
    		addPhraseOffice2007(request, dir, name, number);
			//Range range = doc.getRange();
			//Paragraph paragraph = range.getParagraph(0);
			//paragraph.insertBefore("Numer " + number + "\r");
			//out = new FileOutputStream(path);
			//doc.write(out);
			//saveMessage(request, getText("number.added", new Object[]{number}, request.getLocale()));
			return;
    	} catch (Exception e) {
    		e.printStackTrace();
    		saveMessage(request, getText("errors.docisnotword", request.getLocale()));
    	} /*catch (OfficeXmlFileException oxfe) {
    		log.error(oxfe);
    		try {
				addPhraseOffice2007(request, dir, name, number);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}*/ finally {    		
			try {
				if (stream != null)
					stream.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private void addPhraseOffice2007(HttpServletRequest request, String dir, String name, String number) throws ZipException, IOException, SAXException,
    		ParserConfigurationException, TransformerException {
    	String path = dir + Constants.FILE_SEP + name;

    	File f = new File(path);
    	ZipFile docxFile = new ZipFile(f);
    	
    	Enumeration entriesIter = docxFile.entries();
    	boolean documentFound = false;
		while (entriesIter.hasMoreElements()) {
			ZipEntry entry = (ZipEntry)entriesIter.nextElement();
		    if (entry.getName().equals("word/document.xml"))
		    	documentFound = true;
		}
		if (documentFound) {
			ZipEntry documentXML = docxFile.getEntry("word/document.xml");
			InputStream documentXMLIS = docxFile.getInputStream(documentXML);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			Document doc = dbf.newDocumentBuilder().parse(documentXMLIS);
			
			Element docElement = doc.getDocumentElement();
			
			Element bodyElement = (Element)
			    docElement.getElementsByTagName("w:body").item(0);

			Element pElement = (Element)
				bodyElement.getElementsByTagName("w:p").item(0);

			Element rElement = (Element)
				pElement.getElementsByTagName("w:r").item(0);

			Element tElement = (Element)
			    rElement.getElementsByTagName("w:t").item(0);
			  
			Element theNewP = doc.createElement("w:p");
			Element theNewR = doc.createElement("w:r");
			Element theNewT = doc.createElement("w:t");
			  
			theNewT.setTextContent("Numer: "+ number + "\n");
			theNewR.appendChild(theNewT);
			theNewP.appendChild(theNewR);
			bodyElement.insertBefore(theNewP, pElement);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(baos));
  
			String guid = UUID.randomUUID().toString();
			ZipOutputStream docxOutFile = new ZipOutputStream(
					new FileOutputStream(dir + Constants.FILE_SEP + guid));
			Enumeration entriesIter2 = docxFile.entries();
			while (entriesIter2.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entriesIter2.nextElement();
				if (entry.getName().equals("word/document.xml")) {
					byte[] data = baos.toByteArray();
					docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
					docxOutFile.write(data, 0, data.length);
					docxOutFile.closeEntry();
				} else {
					InputStream incoming = docxFile.getInputStream(entry);
					byte[] data = new byte[1024 * 16];
					int readCount = incoming.read(data, 0, data.length);
					docxOutFile.putNextEntry(new ZipEntry(entry.getName()));
					docxOutFile.write(data, 0, readCount);
					docxOutFile.closeEntry();
				}
			}
			docxOutFile.close();
			  
			File file = new File(dir + Constants.FILE_SEP + guid);
			file.renameTo(new File(dir + Constants.FILE_SEP + name));
			  
			saveMessage(request, getText("number.added", new Object[]{number}, request.getLocale()));
		} else {
			saveMessage(request, getText("errors.docisnotword", request.getLocale()));
		}
		
		docxFile.close();
    }
    
    private void processFiles(HttpServletRequest request, Mail mail) {
		if (StringUtils.isNotEmpty(request.getParameter("deletedFilesIds"))) {
			String[] deletedFiles = request.getParameter("deletedFilesIds").split("_");
			for (String fileId : deletedFiles) {
				if (!"".equals(fileId)) {
					MailFile file = fileManager.getMailFile(Long.parseLong(fileId));
					fileManager.removePhysicalFile(file.getId());
					mail.getFiles().remove(file);
				}
			}
		}
	}
}