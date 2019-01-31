package org.parafia.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * This class represents the file with a document scan uploaded to a database
 *
 * @author <a href="mailto:consultingpd@gmail.com">PDC</a>
 */
@Entity
@Table(name="mails")
public class Mail extends BaseObject {
	private static final long serialVersionUID = 2988139630117552611L;
	
	private Long id;
    private Addressee receiver;
    private String number;
    //private String appendix;
    private Date registerDate;
    private String keyWords;
    private String descriptionName;
    private String path;
    //private String originalName;
    //private String contentType;
    
    private List<MailFile> files = new ArrayList<MailFile>();
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false,length=2047)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="receiver_id", nullable=false)
	public Addressee getReceiver() {
		return receiver;
	}

	public void setReceiver(Addressee receiver) {
		this.receiver = receiver;
	}

	@Column(nullable=false,length=16)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="mail")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<MailFile> getFiles() {
		return files;
	}
	public void setFiles(List<MailFile> files) {
		this.files = files;
	}
	
	

/*	@Column(name="original_name",nullable=false,length=255)
	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@Column(name="content_type",nullable=false,length=128)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}*/
	
	@Column(name="register_date",nullable=false)
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name="key_words",nullable=true,length=2048)
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(name="description_name",nullable=true)
	public String getDescriptionName() {
		return descriptionName;
	}
	public void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Mail)) {
			return false;
		}
		Mail rhs = (Mail) object;
		return new EqualsBuilder().append(this.files, rhs.files).append(this.path, rhs.path).append(
				this.receiver, rhs.receiver).append(this.registerDate,
				rhs.registerDate).append(this.keyWords, rhs.keyWords).append(
				this.number, rhs.number).append(this.descriptionName,
				rhs.descriptionName).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1371277643, 66819297).append(this.files).append(
				this.receiver).append(this.registerDate).append(this.keyWords).append(this.path)
				.append(this.number).append(this.descriptionName).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("registerDate",
				this.registerDate).append("receiver", this.receiver).append(
				"keyWords", this.keyWords).append("files", this.files).append(
				"number", this.number).append("descriptionName",
				this.descriptionName).append("path", this.path).append("id", this.id).toString();
	}
}
