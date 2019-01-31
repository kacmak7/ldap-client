package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="addressees")
public class Addressee extends BaseObject {
	private static final long serialVersionUID = -886889957617155617L;

	private Long id;
	
	private String title;
	private String firstName;
	private String lastName;
	private String institution;
	private String street;
	private String post;
	private String country;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="first_name",nullable=false,length=64)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="last_name",nullable=false,length=64)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(nullable=true,length=128)
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	@Column(nullable=true,length=128)
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Column(nullable=true,length=64)
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(nullable=true,length=64)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(nullable=true,length=50)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public String getFullName() {
		if (StringUtils.isBlank(lastName))
			return institution;
		String result;
		if (title != null)
			result = title;
		else
			result = "";
		if (StringUtils.isNotBlank(firstName))
			result += " " + firstName;
		result += " " + lastName;
		
		return result;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Addressee)) {
			return false;
		}
		Addressee rhs = (Addressee) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.lastName, rhs.lastName).append(
				this.title, rhs.title).append(this.post, rhs.post).append(
				this.street, rhs.street).append(this.firstName, rhs.firstName)
				.append(this.country, rhs.country).append(this.institution,
						rhs.institution).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1679048233, 737067895).append(this.id).append(this.lastName).append(
				this.title).append(this.post).append(this.street).append(
				this.firstName).append(this.country).append(this.institution)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("street", this.street).append(
				"fullName", this.getFullName()).append("country", this.country)
				.append("title", this.title).append("post", this.post).append(
						"institution", this.institution).append("firstName",
						this.firstName).append("lastName", this.lastName)
				.append("id", this.id).toString();
	}
}
