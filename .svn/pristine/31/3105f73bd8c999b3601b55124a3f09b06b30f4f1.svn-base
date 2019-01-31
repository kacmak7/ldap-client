package org.parafia.model.printcards;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.parafia.Constants;
import org.parafia.model.BaseObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="baptismes")
public class Baptism extends BaseObject {
	private static final long serialVersionUID = 8722617440978588844L;

	private Long id;
	
	private String fileNumber;
	private String date;
	private String place;
	private String reason;								//powod wystawienia swiadectwa chrztu
	
	private String confirmationRemarks;
	private String marriageRemarks;
	private String otherRemarks;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="file_number", nullable=false, length=20)
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	@Column
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Transient
	public String getDateDaysFirst() {
		if (date.length() == 4)
			return date;
		else {
			DateFormat fDb = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
			DateFormat fUser = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	        try {
	        	Date d = fDb.parse(date);
	        	return fUser.format(d);
	        } catch (ParseException e) {
	        	e.printStackTrace();
				return date;
			}

		}
	}
	
	@Transient
	public String getDateYearFirst() {
		if (date.length() == 4)
			return date;
		else {
			DateFormat fDb = new SimpleDateFormat(Constants.DATE_FORMAT_DB);
			DateFormat fUser = new SimpleDateFormat(Constants.DATE_FORMAT_USER);
	        try {
	        	Date d = fUser.parse(date);
	        	return fDb.format(d);
	        } catch (ParseException e) {
	        	e.printStackTrace();
				return date;
			}

		}
	}
	
	@Column(length=50)
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Column(length=255)
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name="confirmation_remarks", length=128)
	public String getConfirmationRemarks() {
		return confirmationRemarks;
	}
	public void setConfirmationRemarks(String confirmationRemarks) {
		this.confirmationRemarks = confirmationRemarks;
	}

	@Column(name="marriage_remarks", length=128)
	public String getMarriageRemarks() {
		return marriageRemarks;
	}
	public void setMarriageRemarks(String marriageRemarks) {
		this.marriageRemarks = marriageRemarks;
	}

	@Column(name="other_remarks", length=128)
	public String getOtherRemarks() {
		return otherRemarks;
	}
	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Baptism)) {
			return false;
		}
		Baptism rhs = (Baptism) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.otherRemarks, rhs.otherRemarks)
				.append(this.fileNumber, rhs.fileNumber).append(
						this.confirmationRemarks, rhs.confirmationRemarks)
				.append(this.place, rhs.place).append(this.reason, rhs.reason).append(this.date, rhs.date)
				.append(this.marriageRemarks, rhs.marriageRemarks).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(897055991, -1403802287).append(this.id).append(this.otherRemarks)
				.append(this.fileNumber).append(this.confirmationRemarks)
				.append(this.place).append(this.reason).append(this.date).append(
						this.marriageRemarks).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("date", this.date).append(
				"marriageRemarks", this.marriageRemarks).append("otherRemarks",
				this.otherRemarks).append("confirmationRemarks",
				this.confirmationRemarks).append("fileNumber", this.fileNumber)
				.append("place", this.place).append("reason", this.reason).append("id", this.id).toString();
	}
}
