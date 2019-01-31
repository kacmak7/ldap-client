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
@Table(name="license_assistances")
/**
 * Licencja na asystencje przy zawieraniu malzenstwa
 */
public class LicenseAssistance extends BaseObject {
	private static final long serialVersionUID = -8049902285778665511L;

	private Long id;
	private String remarks;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof LicenseAssistance)) {
			return false;
		}
		LicenseAssistance rhs = (LicenseAssistance) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.remarks, rhs.remarks).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1206133159, -1703504471).append(this.id).append(this.remarks)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("remarks", this.remarks)
				.append("id", this.id).toString();
	}
}
