package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.parafia.Constants;

@Entity
@Table(name="mail_files")
public class MailFile {
	private Long id;
	private String contentType;
	private String name;
	private String originalName;
	private Mail mail;
	private long size;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="content_type")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="original_name")
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="mail_id", nullable=false)
	public Mail getMail() {
		return mail;
	}
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	
	@Column
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	@Transient
	public String getPath() {
		if (getMail() == null || getMail().getPath() == null || getName() == null)
			return null;
		else
			return getMail().getPath() + Constants.FILE_SEP + getName();
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof MailFile)) {
			return false;
		}
		MailFile rhs = (MailFile) object;
		
		return new EqualsBuilder().append(this.mail.getId(), rhs.mail.getId()).append(this.name,
				rhs.name).append(this.originalName, rhs.originalName).append(
				this.contentType, rhs.contentType).append(this.size, rhs.size)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1828354999, 282767987).appendSuper(
				super.hashCode()).append(this.id).append(this.mail.getId()).append(
				this.name).append(this.originalName).append(this.contentType)
				.append(this.size).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("contentType", this.contentType).append("originalName",
						this.originalName).append("name", this.name).append(
						"mail", this.mail).append("size", this.size).append(
						"id", this.id).toString();
	}
}
