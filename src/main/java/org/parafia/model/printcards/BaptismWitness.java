package org.parafia.model.printcards;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.parafia.model.BaseObject;
import org.parafia.model.dictionary.Practising;

@Embeddable
public class BaptismWitness extends BaseObject {
	private static final long serialVersionUID = 6531765463558409399L;

	private String firstName;
	private String surname;
	private String mothersFirstName;
	private String mothersLastName;
	private String fathersFirstName;
	private String fathersLastName;
	
	private Date birthDate;
	private String job;
	private String religion;
	private Practising practising = new Practising();
	private String address;
	
	@Transient
	public Long getId() {
		return null;
	}

	@Column(nullable=false,length=50,name="first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(nullable=false,length=50)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Column(name="mothers_first_name", length=25)
	public String getMothersFirstName() {
		return mothersFirstName;
	}
	public void setMothersFirstName(String mothersFirstName) {
		this.mothersFirstName = mothersFirstName;
	}

	@Column(name="mothers_last_name", length=25)
	public String getMothersLastName() {
		return mothersLastName;
	}
	public void setMothersLastName(String mothersLastName) {
		this.mothersLastName = mothersLastName;
	}

	@Column(name="fathers_first_name", length=25)
	public String getFathersFirstName() {
		return fathersFirstName;
	}
	public void setFathersFirstName(String fathersFirstName) {
		this.fathersFirstName = fathersFirstName;
	}

	@Column(name="fathers_last_name", length=25)
	public String getFathersLastName() {
		return fathersLastName;
	}
	public void setFathersLastName(String fathersLastName) {
		this.fathersLastName = fathersLastName;
	}
	
	@Column(nullable=true, name="birth_date")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Column(nullable=true, length=50)
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	@Column(nullable=false, length=50)
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="practising_id", nullable=true)
	public Practising getPractising() {
		return practising;
	}
	public void setPractising(Practising practising) {
		this.practising = practising;
	}
	
	@Column(nullable=false, length=150)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Transient
	public String getFullName() {
		String result = "";
		result += firstName;
		if (surname != null) {
			result += " " + surname;
		}
		return result;
	}
	
	@Transient
	public String getMothersFullName() {
		if (mothersFirstName == null || mothersLastName == null)
			return null;
		else
			return mothersFirstName + " " + mothersLastName;
	}
	
	@Transient
	public String getFathersFullName() {
		if (fathersFirstName == null || fathersLastName == null)
			return null;
		else
			return fathersFirstName + " " + fathersLastName;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BaptismWitness)) {
			return false;
		}
		BaptismWitness rhs = (BaptismWitness) object;
		return new EqualsBuilder().append(
				this.religion, rhs.religion).append(
				this.fathersFirstName, rhs.fathersFirstName).append(
				this.practising, rhs.practising).append(this.address,
				rhs.address)
				.append(this.mothersFirstName, rhs.mothersFirstName).append(
						this.fathersLastName, rhs.fathersLastName).append(
						this.mothersLastName, rhs.mothersLastName).append(
						this.job, rhs.job).append(this.surname, rhs.surname)
				.append(this.birthDate, rhs.birthDate).append(this.firstName,
						rhs.firstName).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1149576269, -1641949353).append(this.religion).append(
				this.fathersFirstName).append(this.practising).append(
				this.address).append(this.mothersFirstName).append(
				this.fathersLastName).append(this.mothersLastName).append(
				this.job).append(this.surname).append(this.birthDate).append(
				this.firstName).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("fathersLastName",
				this.fathersLastName).append("surname", this.surname).append(
				"fathersFirstName", this.fathersFirstName).append("job",
				this.job).append("religion", this.religion).append(
				"mothersFirstName", this.mothersFirstName).append("birthDate",
				this.birthDate).append("practising", this.practising).append(
				"mothersLastName", this.mothersLastName).append("firstName",
				this.firstName).append("address",
				this.address).toString();
	}
}
