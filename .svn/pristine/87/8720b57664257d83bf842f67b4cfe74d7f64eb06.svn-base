package org.parafia.model.printcards;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.parafia.model.BaseObject;
import org.parafia.model.InvestigationProtocole;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name="marriages")
public class Marriage extends BaseObject {
	private static final long serialVersionUID = -6493227822445436425L;

	private Long id;
	
	private String parish;
	private String religion;
	private String baptismDate;
	private String baptismChurch;
	private String baptismChurchIn;
	private String baptismNumber;
	private String churchPostAddress;
	private Date confirmationDate;
	private String confirmationChurch;
	
	private InvestigationProtocole protocole;
	
	//private boolean fianceeIsWoman;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=127)
	public String getParish() {
		return parish;
	}
	public void setParish(String parish) {
		this.parish = parish;
	}
	
	@Column(length=63)
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	
	@Column(name="baptism_date")
	public String getBaptismDate() {
		return baptismDate;
	}
	public void setBaptismDate(String baptismDate) {
		this.baptismDate = baptismDate;
	}
	
	@Column(name="baptism_church", length=127)
	public String getBaptismChurch() {
		return baptismChurch;
	}
	public void setBaptismChurch(String baptismChurch) {
		this.baptismChurch = baptismChurch;
	}
	
	@Column(name="baptism_church_in", length=127)
	public String getBaptismChurchIn() {
		return baptismChurchIn;
	}
	public void setBaptismChurchIn(String baptismChurchIn) {
		this.baptismChurchIn = baptismChurchIn;
	}
	
	@Column(name="baptism_number", length=31)
	public String getBaptismNumber() {
		return baptismNumber;
	}
	public void setBaptismNumber(String baptismNumber) {
		this.baptismNumber = baptismNumber;
	}
	
	@Column(name="church_post_address", length=127)
	public String getChurchPostAddress() {
		return churchPostAddress;
	}
	public void setChurchPostAddress(String churchPostAddress) {
		this.churchPostAddress = churchPostAddress;
	}
	
	@Column(name="confirmation_date")
	public Date getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	
	@Column(name="confirmation_church", length=127)
	public String getConfirmationChurch() {
		return confirmationChurch;
	}
	public void setConfirmationChurch(String confirmationChurch) {
		this.confirmationChurch = confirmationChurch;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public InvestigationProtocole getProtocole() {
		return protocole;
	}
	public void setProtocole(InvestigationProtocole protocole) {
		this.protocole = protocole;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Marriage)) {
			return false;
		}
		Marriage rhs = (Marriage) object;
		return new EqualsBuilder().append(
				this.protocole, rhs.protocole).append(this.religion,
				rhs.religion).append(this.id, rhs.id).append(
				this.baptismChurch, rhs.baptismChurch).append(
				this.churchPostAddress, rhs.churchPostAddress).append(
				this.baptismChurchIn, rhs.baptismChurchIn).append(
				this.baptismDate, rhs.baptismDate).append(
				this.confirmationDate, rhs.confirmationDate).append(
				this.parish, rhs.parish).append(this.confirmationChurch,
				rhs.confirmationChurch).append(this.baptismNumber,
				rhs.baptismNumber).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-580730655, 1821032889).append(this.protocole).append(this.religion)
				.append(this.id).append(this.baptismChurch).append(
						this.churchPostAddress).append(this.baptismChurchIn)
				.append(this.baptismDate).append(this.confirmationDate).append(
						this.parish).append(this.confirmationChurch).append(
						this.baptismNumber).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("baptismChurch",
				this.baptismChurch).append("baptismDate", this.baptismDate)
				.append("religion", this.religion).append("protocole",
						this.protocole).append("confirmationDate",
						this.confirmationDate).append("baptismNumber",
						this.baptismNumber).append("churchPostAddress",
						this.churchPostAddress).append("baptismChurchIn",
						this.baptismChurchIn).append("confirmationChurch",
						this.confirmationChurch).append("parish", this.parish)
				.append("id", this.id).toString();
	}
}
