package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.parafia.model.dictionary.TakePart;

@Entity
@Table(name="parents")
public class Parent extends Person{
	private static final long serialVersionUID = 3847656599689667416L;

	private String job;
	private TakePart takePartMass;
	private TakePart takePartSacrament;
	
	private Family family;

	@Override
	@Column(nullable = true, length = 50)
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
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
	
	@Transient
	public Family getFamily() {
		return family;
	}
	
	public void setFamily(Family family) {
		this.family = family;
	}

	@Transient
	@Override
	@Deprecated
	public String getSurnameNotNull() {
		/*if (StringUtils.isNotBlank(super.getSurname()))
			return super.getSurname();
		else if (getFamily() != null)
			return getFamily().getSurname();
		else
			return null;*/
		return getSurname();
	}
	
	@Transient
	@Override
	public String getFullName() {
		String result = "";
		result += getFirstName();
		if (StringUtils.isNotBlank(getSurname()))
			result += " " + getSurname();
		else
			result += " " + getFamily().getSurname();
			
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Parent)) {
			return false;
		}
		Parent rhs = (Parent) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.job, rhs.job).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1031228501, -1071302501).appendSuper(
				super.hashCode()).append(this.job).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("job", this.job).append(
				"takePartSacrament", this.getTakePartSacrament()).append(
				"surname", this.getSurname()).append("birthDate",
				this.getBirthDate()).append(
				"takePartMass", this.getTakePartMass()).append("firstName",
				this.getFirstName()).append(
				"id", this.getId()).toString();
	}
}
