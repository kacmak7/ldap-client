package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="grave_persons")
public class GravePerson extends BaseObject {
	private static final long serialVersionUID = -6329374812609904423L;

	private Long id;
	private String firstName;
	private String surname;
	private String deathDate;
	private GraveNiche grave;
	
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
	
	@Column(length=10,name="death_date")
	public String getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="grave_id", nullable=false)
	public GraveNiche getGrave() {
		return grave;
	}
	public void setGrave(GraveNiche grave) {
		this.grave = grave;
	}
	
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GravePerson)) {
			return false;
		}
		GravePerson rhs = (GravePerson) object;
		return new EqualsBuilder().append(this.surname, rhs.surname).append(
				this.grave, rhs.grave).append(this.firstName, rhs.firstName)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1672512231, -806776735).append(this.surname).append(
				this.grave).append(this.firstName).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("grave", this.grave).append(
				"surname", this.surname).append("firstName", this.firstName)
				.append("id", this.id).toString();
	}
}
