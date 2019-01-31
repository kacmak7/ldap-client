package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Embeddable
public class GraveOwner extends BaseObject {
	private static final long serialVersionUID = -2294124764931868090L;
	
	private String firstName;
	private String surname;
	private Address address = new Address();
	private String phone;
	
	@Transient
	public Long getId() {
		return null;
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
	
	@Embedded	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Column(nullable=true,length=25)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GraveOwner)) {
			return false;
		}
		GraveOwner rhs = (GraveOwner) object;
		return new EqualsBuilder().append(
				this.phone, rhs.phone).append(this.address, rhs.address)
				.append(this.surname, rhs.surname).append(this.firstName,
						rhs.firstName).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1298967421, 128282059).append(this.phone).append(this.address)
				.append(this.surname).append(this.firstName).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("phone", this.phone).append(
				"surname", this.surname).append("firstName", this.firstName)
				.append("id", this.getId()).append("address", this.address)
				.toString();
	}
}
