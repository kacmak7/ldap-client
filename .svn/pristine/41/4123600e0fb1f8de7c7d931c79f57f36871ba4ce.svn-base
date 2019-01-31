package org.parafia.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.parafia.model.printcards.Remaining;

@Entity
@Table(name="others_wo_families")
public class OtherWoFamily extends Person {
	
	private static final long serialVersionUID = 1567739189837714123L;

//	private Address address = null;
	
	@Override
	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", nullable = true)
	public Address getAddress() {
		return this.address;
	}
	
	@Override
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	@Transient
	public String getFullName() {
		String result = getFirstName();
		if (StringUtils.isNotBlank(getSurname()))
			result += " " + getSurname();
		return result;
	}
	
	@Transient
	@Override
	public boolean isWithoutFamily() {
		return true;
	}
	
	@Transient
	@Override
	public String getSurnameNotNull() {
		return super.getSurname();
	}
	
	@Override
	@Transient
	public String getJob() {
		return null;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OtherWoFamily)) {
			return false;
		}
		return new EqualsBuilder().appendSuper(super.equals(object)).isEquals();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(728822043, 2145998613).appendSuper(
				super.hashCode()).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("fullName", this.getFullName())
				.append("remaining", this.getRemaining()).append("surname",
						this.getSurname()).append("birthDate",
						this.getBirthDate()).append("firstName",
						this.getFirstName()).append("id", this.getId())
				.toString();
	}
}