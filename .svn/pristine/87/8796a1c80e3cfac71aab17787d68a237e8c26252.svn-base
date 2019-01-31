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
@Table(name="addresses")
public class Address extends BaseObject {
	private static final long serialVersionUID = -1369346245116275844L;

	private Long id;
	
	private String postCode;
	private String street;
	private String place;
	private String firstNumber;
	private String lastNumber;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=6)
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Column(length=40)
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Column(length=6, name="first_number")
	public String getFirstNumber() {
		return firstNumber;
	}
	public void setFirstNumber(String firstNumber) {
		this.firstNumber = firstNumber;
	}
	
	@Column(length=6, name="last_number")
	public String getLastNumber() {
		return lastNumber;
	}
	public void setLastNumber(String lastNumber) {
		this.lastNumber = lastNumber;
	}
	
	@Column(length=50)
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}

	@Transient
	public String getFullString() {
		StringBuffer sb = new StringBuffer();
		sb.append(street).append(" ").append(firstNumber);
		if (StringUtils.isNotBlank(lastNumber))
			sb.append(" m. ").append(lastNumber);
		if (place != null)
			sb.append(" ").append(postCode).append(" ").append(place);
		return sb.toString().trim();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Address)) {
			return false;
		}
		Address rhs = (Address) object;
		return new EqualsBuilder().append(
				this.lastNumber, rhs.lastNumber)
				.append(this.street, rhs.street).append(this.postCode,
						rhs.postCode).append(this.firstNumber, rhs.firstNumber)
				.append(this.place, rhs.place).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-2082141327, 1587230311)
				.append(this.lastNumber).append(this.street)
				.append(this.postCode).append(this.firstNumber).append(
						this.place).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("street", this.street).append(
				"firstNumber", this.firstNumber).append("postCode",
				this.postCode).append("place", this.place).append("fullString",
				this.getFullString()).append("lastNumber", this.lastNumber)
				.append("id", this.getId()).toString();
	}
}
