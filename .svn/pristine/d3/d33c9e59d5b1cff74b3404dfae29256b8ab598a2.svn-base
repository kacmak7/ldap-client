package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Proxy;
import org.parafia.model.dictionary.TakePart;

@Entity
@Table(name="children")
public class Child extends Person{
	private static final long serialVersionUID = -617427593554091362L;
	private String religiousFormation;
	private Family family;
	private TakePart takePartMass;
	private TakePart takePartSacrament;

	@Column(name="religious_formation", nullable=true)
	public String getReligiousFormation() {
		return religiousFormation;
	}

	public void setReligiousFormation(String religiousFormation) {
		this.religiousFormation = religiousFormation;
	}
	
	@ManyToOne(optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name="family_id", nullable=false)
	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="take_part_mass_id", nullable=false)
	public TakePart getTakePartMass() {
		return takePartMass;
	}
	public void setTakePartMass(TakePart takePartMass) {
		this.takePartMass = takePartMass;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="take_part_sacrament_id", nullable=false)
	public TakePart getTakePartSacrament() {
		return takePartSacrament;
	}
	public void setTakePartSacrament(TakePart takePartSacrament) {
		this.takePartSacrament = takePartSacrament;
	}
	
	@Override
	@Transient
	public String getFullName() {
		String result = "";
		result += getFirstName();
		if (StringUtils.isNotBlank(getSurname()))
			result += " " + getSurname();
		//else
		//	result += " " + getFamily().getSurname();
			
		return result;
	}
	
	@Override
	@Transient
	public Address getAddress() {
		if (getFamily() != null)
			return getFamily().getAddress();
		else
			return null;
	}
	
	@Override
	@Transient
	public String getJob() {
		return null;
	}
	
	@Transient
	@Override
	@Deprecated
	/**
	 * Nie mozemy ladowac rodziny, zeby wziac surname - zbyt dlugo sie to wykonuje
	 */
	public String getSurnameNotNull() {
		/*if (StringUtils.isNotBlank(super.getSurname()))
			return super.getSurname();
		else if (family != null)
			return family.getSurname();
		else
			return null;*/
		return getSurname();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Child)) {
			return false;
		}
		Child rhs = (Child) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.family, rhs.family).append(this.religiousFormation,
				rhs.religiousFormation).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1317614463, -563697183).appendSuper(
				super.hashCode()).append(this.family).append(
				this.religiousFormation).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("religiousFormation",
				this.religiousFormation).append("takePartSacrament",
				this.getTakePartSacrament()).append("surname",
				this.getSurname()).append("birthDate", this.getBirthDate())
				.append("takePartMass", this.getTakePartMass()).append(
						"firstName", this.getFirstName()).append("family",
						this.family).append("id", this.getId()).toString();
	}
}