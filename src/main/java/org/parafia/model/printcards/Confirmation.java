package org.parafia.model.printcards;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.parafia.model.BaseObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="confirmations")
public class Confirmation extends BaseObject {
	private static final long serialVersionUID = 4557061693876376523L;

	private Long id;
	
	private String fileNumber;
	private Date date;
	private String place;
	
	private String confirmationName;
	private String bishop;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="file_number", nullable=false)
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	@Column(nullable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(nullable=false, length=50)
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Column(name="confirmation_name", length=25)
	public String getConfirmationName() {
		return confirmationName;
	}
	public void setConfirmationName(String confirmationName) {
		this.confirmationName = confirmationName;
	}

	@Column(length=50)
	public String getBishop() {
		return bishop;
	}
	public void setBishop(String bishop) {
		this.bishop = bishop;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Confirmation)) {
			return false;
		}
		Confirmation rhs = (Confirmation) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.confirmationName,
				rhs.confirmationName).append(this.bishop, rhs.bishop).append(
				this.fileNumber, rhs.fileNumber).append(this.place, rhs.place)
				.append(this.date, rhs.date).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1090567773, -1188716935).append(this.id).append(this.confirmationName)
				.append(this.bishop).append(this.fileNumber).append(this.place)
				.append(this.date).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("date", this.date).append(
				"confirmationName", this.confirmationName).append("fileNumber",
				this.fileNumber).append("place", this.place).append("bishop",
				this.bishop).append("id", this.id).toString();
	}
}
