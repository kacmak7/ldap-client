package org.parafia.model.printfiancees;

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
@Table(name="dispensations")
/**
 * Dyspensa
 */
public class Dispensation extends BaseObject {
	private static final long serialVersionUID = 3827814656378644278L;

	private Long id;
	private String sacrament;
	private String reason;
	private boolean underAge;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public String getSacrament() {
		return sacrament;
	}
	public void setSacrament(String sacrament) {
		this.sacrament = sacrament;
	}
	
	@Column
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Column(name="under_age")	
	public boolean isUnderAge() {
		return underAge;
	}
	public void setUnderAge(boolean underAge) {
		this.underAge = underAge;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Dispensation)) {
			return false;
		}
		Dispensation rhs = (Dispensation) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.reason, rhs.reason).append(
				this.sacrament, rhs.sacrament).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-2053172861, 358716043).append(this.id).append(this.reason).append(
				this.sacrament).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("reason", this.reason).append(
				"sacrament", this.sacrament).append("id", this.id).toString();
	}
}
