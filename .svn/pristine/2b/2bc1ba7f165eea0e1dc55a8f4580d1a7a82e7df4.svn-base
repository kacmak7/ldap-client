package org.parafia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.parafia.model.printcards.Remaining;

@Entity
@Table(name="persons")
@Inheritance(strategy=InheritanceType.JOINED)
public class Person implements Serializable { 	// deleted extends BaseObject 
	private static final long serialVersionUID = -18640212145323953L;

	public enum Sex { FEMINE, MASCULINE };
	
	private Long id;
	private String firstName;
	private String surname;
	private String birthDate;
	private Remaining remaining;
	protected Address address;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=50,name="first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(length=50)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Column(nullable=true, name="birth_date", length=10)
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Remaining getRemaining() {
		return remaining;
	}
	public void setRemaining(Remaining remaining) {
		this.remaining = remaining;
	}
	
	@Transient
	public String getFullName() {
		return firstName + " " + surname;
	}
	
	@Transient
	public String getSurnameNotNull() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transient
	public Address getAddress() {
		return address;
	}

	@Transient
	public void setAddress(Address address) {
		this.address = address;
	}

	@Transient
	public String getJob() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transient
	public boolean isWithoutFamily() {
		return false;
	}
	
	@Transient
	public String getBaptismDate() {
		if (getRemaining() != null) {
			if (getRemaining().getBaptism() != null)
				return getRemaining().getBaptism().getDateDaysFirst();
			else if (getRemaining().getMarriage() != null)
				return getRemaining().getMarriage().getBaptismDate();
		}
		return null;
	}
	
	@Transient
	public String getBaptismPlace() {
		if (getRemaining() != null) {
			if (getRemaining().getBaptism() != null)
				return getRemaining().getBaptism().getPlace();
			else if (getRemaining().getMarriage() != null)
				return getRemaining().getMarriage().getBaptismChurchIn();
		}
		return null;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Person)) {
			return false;
		}
		Person rhs = (Person) object;
		return new EqualsBuilder().append(this.surname, rhs.surname)
				.append(this.birthDate, rhs.birthDate).append(this.firstName,
						rhs.firstName).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-552047961, 350835555).append(this.surname)
			.append(this.birthDate).append(this.firstName)
			.toHashCode();
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", surname=" + surname + ", birthDate=" + birthDate
				+ ", remaining=" + remaining + ", address=" + address + "]";
	}


}