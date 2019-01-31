package org.parafia.webapp.controller.commands;

import java.io.Serializable;

import org.parafia.model.Addressee;


/**
 * Command class to handle uploading of a file.
 *
 * <p>
 * <a href="Mail.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */

@Deprecated
public class Mail implements Serializable {
	private static final long serialVersionUID = 8318540817115681916L;

	private byte[] file;
    private Addressee receiver;
    private String number;
    private String path;
    private String keyWords;
    private String descriptionName;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

	public Addressee getReceiver() {
		return receiver;
	}

	public void setReceiver(Addressee receiver) {
		this.receiver = receiver;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getDescriptionName() {
		return descriptionName;
	}

	public void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}
}
