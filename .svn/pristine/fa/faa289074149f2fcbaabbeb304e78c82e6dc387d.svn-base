package org.parafia.model.printcards;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.parafia.model.BaseObject;

@Entity
@Table(name="baptism_preparations")
public class BaptismPreparation extends BaseObject {
	private static final long serialVersionUID = 1238114023097866658L;

	private Long id;
	private String preparedBy;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="prepared_by", nullable=false, length=255)
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BaptismPreparation)) {
			return false;
		}
		BaptismPreparation rhs = (BaptismPreparation) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.preparedBy, rhs.preparedBy).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(943745495, 61553707).append(this.id).append(this.preparedBy).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("preparedBy",
				this.preparedBy).append("id", this.id).toString();
	}
}
