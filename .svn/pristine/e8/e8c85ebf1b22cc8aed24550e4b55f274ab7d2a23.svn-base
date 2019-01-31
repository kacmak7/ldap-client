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
@Table(name="baptism_changes")
/**
 * Zmiany w akcie chrztu
 */
public class BaptismChanges extends BaseObject {
	private static final long serialVersionUID = 1784309068662562394L;

	private Long id;
	
	private String personalData;
	private String civilPersonalData;
	private String civilDocuments;
	private String differences;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=true, name="personal_data")
	public String getPersonalData() {
		return personalData;
	}
	public void setPersonalData(String personalData) {
		this.personalData = personalData;
	}
	
	@Column(nullable=true, name="civil_personal_data")
	public String getCivilPersonalData() {
		return civilPersonalData;
	}
	public void setCivilPersonalData(String civilPersonalData) {
		this.civilPersonalData = civilPersonalData;
	}
	
	@Column(nullable=true, name="civil_documents")	
	public String getCivilDocuments() {
		return civilDocuments;
	}
	public void setCivilDocuments(String civilDocuments) {
		this.civilDocuments = civilDocuments;
	}
	
	@Column(nullable=true, name="differences")
	public String getDifferences() {
		return differences;
	}
	public void setDifferences(String differences) {
		this.differences = differences;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BaptismChanges)) {
			return false;
		}
		BaptismChanges rhs = (BaptismChanges) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.personalData, rhs.personalData)
				.append(this.civilDocuments, rhs.civilDocuments).append(
						this.civilPersonalData, rhs.civilPersonalData).append(
						this.differences, rhs.differences).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1348201323, -1624709767).append(this.id).append(this.personalData)
				.append(this.civilDocuments).append(this.civilPersonalData)
				.append(this.differences).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("differences", this.differences).append("personalData",
						this.personalData).append("civilPersonalData",
						this.civilPersonalData).append("civilDocuments",
						this.civilDocuments).append("id", this.id).toString();
	}
}
