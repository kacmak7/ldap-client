package org.parafia.model.printfiancees;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.parafia.model.BaseObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="assistance_delagations")
/**
 * Delegacja do asystencji przy zawieraniu malzenstwa
 */
public class AssistanceDelegation extends BaseObject {
	private static final long serialVersionUID = 1713176077160728662L;

	private Long id;
	
	private String priestFirstName;
	private String priestSurname;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="priest_first_name", nullable=false, length=50)
	public String getPriestFirstName() {
		return priestFirstName;
	}
	public void setPriestFirstName(String priestFirstName) {
		this.priestFirstName = priestFirstName;
	}
	
	@Column(name="priest_surname", nullable=false, length=50)
	public String getPriestSurname() {
		return priestSurname;
	}
	public void setPriestSurname(String priestSurname) {
		this.priestSurname = priestSurname;
	}
	
	@Transient
	public String getPriestFullName() {
		if (priestFirstName == null || priestSurname == null)
			return null;
		else
			return priestFirstName + " " + priestSurname;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AssistanceDelegation)) {
			return false;
		}
		AssistanceDelegation rhs = (AssistanceDelegation) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.priestSurname, rhs.priestSurname)
				.append(this.priestFirstName, rhs.priestFirstName).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-577611665, 1567072139).append(this.id).append(this.priestSurname)
				.append(this.priestFirstName).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("priestFirstName",
				this.priestFirstName).append("priestSurname",
				this.priestSurname).append("id", this.id).toString();
	}
}
