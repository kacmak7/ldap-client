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
@Table(name="deaths")
public class Death extends BaseObject {
	private static final long serialVersionUID = -6374102468094328598L;

	private Long id;
	
	private String fileNumber;
	private Date date;
	private Date burialDate;
	private String place;
	
	private String burialPlace;
	
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
	
	@Column(nullable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="burial_date")
	public Date getBurialDate() {
		return burialDate;
	}
	public void setBurialDate(Date burialDate) {
		this.burialDate = burialDate;
	}
	
	@Column(nullable=false, length=50)
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}

	@Column(length=50, name="burial_place")
	public String getBurialPlace() {
		return burialPlace;
	}
	public void setBurialPlace(String burialPlace) {
		this.burialPlace = burialPlace;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Death)) {
			return false;
		}
		Death rhs = (Death) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.burialPlace, rhs.burialPlace)
				.append(this.fileNumber, rhs.fileNumber)
				.append(this.place, rhs.place)
				.append(this.date, rhs.date)
				.append(this.burialDate, rhs.burialDate).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-308234023, 2120397531).
				append(this.id).append(this.burialPlace)
				.append(this.fileNumber).append(this.place)
				.append(this.date).append(this.burialDate)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("burialPlace", this.burialPlace).append("id", this.id)
				.toString();
	}
}
