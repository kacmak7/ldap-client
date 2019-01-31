package org.parafia.model.printfiancees;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.parafia.model.BaseObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="certificates")
/**
 * ZASWIADCZENIE stanowiace podstawe sporzadzenia aktu malzenstwa
 */
public class Certificate extends BaseObject {
	private static final long serialVersionUID = -7060358144265037679L;

	private Long id;
	private String firstWitnessFirstName;
	private String firstWitnessSurname;
	private String secondWitnessFirstName;
	private String secondWitnessSurname;
	
	private String priestFullName;
	private String priestPosition;
	
	private String number;
	private String marriageNumber;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="first_witness_first_name", nullable=false, length=25)
	public String getFirstWitnessFirstName() {
		return firstWitnessFirstName;
	}
	public void setFirstWitnessFirstName(String firstWitnessFirstName) {
		this.firstWitnessFirstName = firstWitnessFirstName;
	}
	
	@Column(name="first_witness_surname", nullable=false, length=25)
	public String getFirstWitnessSurname() {
		return firstWitnessSurname;
	}
	public void setFirstWitnessSurname(String firstWitnessSurname) {
		this.firstWitnessSurname = firstWitnessSurname;
	}
	
	@Column(name="second_witness_first_name", nullable=false, length=25)
	public String getSecondWitnessFirstName() {
		return secondWitnessFirstName;
	}
	public void setSecondWitnessFirstName(String secondWitnessFirstName) {
		this.secondWitnessFirstName = secondWitnessFirstName;
	}
	
	@Column(name="second_witness_surname", nullable=false, length=25)
	public String getSecondWitnessSurname() {
		return secondWitnessSurname;
	}
	public void setSecondWitnessSurname(String secondWitnessSurname) {
		this.secondWitnessSurname = secondWitnessSurname;
	}
	
	@Column(name="priest_position", nullable=true, length=50)
	public String getPriestPosition() {
		return priestPosition;
	}
	public void setPriestPosition(String priestPosition) {
		this.priestPosition = priestPosition;
	}
	
	@Column(name="priest_full_name", nullable=true, length=50)
	public String getPriestFullName() {
		return priestFullName;
	}
	public void setPriestFullName(String priestFullName) {
		this.priestFullName = priestFullName;
	}
	
	@Column(nullable=false, length=12)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column(name="marriage_number", nullable=false, length=12)
	public String getMarriageNumber() {
		return marriageNumber;
	}
	public void setMarriageNumber(String marriageNumber) {
		this.marriageNumber = marriageNumber;
	}
	
	@Transient
	public String getFirstWitnessFullName() {
		if (StringUtils.isNotBlank(firstWitnessFirstName) && StringUtils.isNotBlank(firstWitnessSurname))
			return firstWitnessSurname + " " + firstWitnessFirstName;
		else
			return null;
	}
	
	@Transient
	public String getSecondWitnessFullName() {
		if (StringUtils.isNotBlank(secondWitnessFirstName) && StringUtils.isNotBlank(secondWitnessSurname))
			return  secondWitnessSurname + " " + secondWitnessFirstName;
		else
			return null;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Certificate)) {
			return false;
		}
		Certificate rhs = (Certificate) object;
		return new EqualsBuilder()
				.append(this.marriageNumber, rhs.marriageNumber)
				.append(this.id, rhs.id)
				.append(this.secondWitnessSurname, rhs.secondWitnessSurname)
				.append(this.firstWitnessFirstName, rhs.firstWitnessFirstName)
				.append(this.secondWitnessFirstName, rhs.secondWitnessFirstName)
				.append(this.number, rhs.number).append(this.priestPosition,
						rhs.priestPosition).append(this.priestFullName,
						rhs.priestFullName).append(this.firstWitnessSurname,
						rhs.firstWitnessSurname).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(2021980715, 1608051511)
				.append(this.marriageNumber).append(this.id)
				.append(this.secondWitnessSurname).append(
						this.firstWitnessFirstName).append(
						this.secondWitnessFirstName).append(this.number)
				.append(this.priestPosition).append(this.priestFullName)
				.append(this.firstWitnessSurname).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("firstWitnessSurname",
				this.firstWitnessSurname).append("firstWitnessFirstName",
				this.firstWitnessFirstName).append("secondWitnessFullName",
				this.getSecondWitnessFullName()).append("marriageNumber",
				this.marriageNumber).append("secondWitnessSurname",
				this.secondWitnessSurname).append("number", this.number)
				.append("priestPosition", this.priestPosition).append(
						"firstWitnessFullName", this.getFirstWitnessFullName())
				.append("priestFullName", this.priestFullName).append(
						"secondWitnessFirstName", this.secondWitnessFirstName)
				.append("id", this.id).toString();
	}
}
