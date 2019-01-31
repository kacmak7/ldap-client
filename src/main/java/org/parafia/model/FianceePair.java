package org.parafia.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.parafia.Constants;
import org.parafia.model.printfiancees.AssistanceDelegation;
import org.parafia.model.printfiancees.Certificate;
import org.parafia.model.printfiancees.Dispensation;
import org.parafia.model.printfiancees.LicenseAssistance;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="fiancee_pairs", uniqueConstraints={@UniqueConstraint(columnNames={"fiancee_he_id", "fiancee_she_id"})})
public class FianceePair extends BaseObject {
	private static final long serialVersionUID = -8784597690109704744L;

	private Long id;
	
	private Person fianceeHe = new OtherWoFamily();
	private Person fianceeShe = new OtherWoFamily();
	
	private Integer protocoleNumber;
	private Date protocoleDate;
	private Dispensation dispensation;
	private AssistanceDelegation assistanceDelegation;
	private LicenseAssistance licenseAssistance;
	
	@NotEmpty(message="{errors.value.notnull}")
	private Date marriageDate;
	
	private String marriageTime;	
	private String fileNumber;
	private String place;
	
	private Certificate certificate;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="fiancee_he_id", nullable=true)
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	public Person getFianceeHe() {
		return fianceeHe;
	}
	public void setFianceeHe(Person fianceeHe) {
		this.fianceeHe = fianceeHe;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="fiancee_she_id", nullable=true)
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE,CascadeType.PERSIST})
	public Person getFianceeShe() {
		return fianceeShe;
	}
	public void setFianceeShe(Person fianceeShe) {
		this.fianceeShe = fianceeShe;
	}
	
	@Column(name="protocole_number")	
	public Integer getProtocoleNumber() {
		return protocoleNumber;
	}
	public void setProtocoleNumber(Integer protocoleNumber) {
		this.protocoleNumber = protocoleNumber;
	}
	
	@Column(name="protocole_date")
	public Date getProtocoleDate() {
		return protocoleDate;
	}
	public void setProtocoleDate(Date protocoleDate) {
		this.protocoleDate = protocoleDate;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Dispensation getDispensation() {
		return dispensation;
	}
	public void setDispensation(Dispensation dispensation) {
		this.dispensation = dispensation;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public AssistanceDelegation getAssistanceDelegation() {
		return assistanceDelegation;
	}
	public void setAssistanceDelegation(AssistanceDelegation assistanceDelegation) {
		this.assistanceDelegation = assistanceDelegation;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public LicenseAssistance getLicenseAssistance() {
		return licenseAssistance;
	}
	public void setLicenseAssistance(LicenseAssistance licenseAssistance) {
		this.licenseAssistance = licenseAssistance;
	}

	@Column(name="marriage_date")
	public Date getMarriageDate() {
		return marriageDate;
	}
	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}
	
	@Column(name="marriage_time", length=5)
	public String getMarriageTime() {
		return marriageTime;
	}
	public void setMarriageTime(String marriageTime) {
		this.marriageTime = marriageTime;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	
	@Column(name="file_number", length=63)
	/**
	 * Numer w ksiedze malzenstw
	 */
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	@Column(length=63)
	/**
	 * Miejsce zawarcia malzenstwa
	 */
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Transient
	public String getFullNumber() {
		if (protocoleNumber != null && protocoleDate != null) {
			DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR);
			return protocoleNumber + "/" + df.format(protocoleDate);
		} else
			return null;
	}
	
	@Transient
	public Person getOtherFiancee(Person person) {
		if (person == null || fianceeHe == null || fianceeShe == null)
			return null;
		else if (person.getId().equals(fianceeHe.getId()))
			return fianceeShe;
		else
			return fianceeHe;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FianceePair)) {
			return false;
		}
		FianceePair rhs = (FianceePair) object;
		return new EqualsBuilder().append(
				this.protocoleNumber, rhs.protocoleNumber).append(
				this.fianceeHe, rhs.fianceeHe).append(this.protocoleDate,
				rhs.protocoleDate).append(this.assistanceDelegation,
				rhs.assistanceDelegation).append(this.licenseAssistance,
				rhs.licenseAssistance).append(this.id, rhs.id).append(
				this.certificate, rhs.certificate).append(this.fileNumber,
				rhs.fileNumber).append(this.dispensation, rhs.dispensation)
				.append(this.fianceeShe, rhs.fianceeShe).append(
						this.marriageDate, rhs.marriageDate).append(
						this.marriageTime, rhs.marriageTime).append(this.place,
						rhs.place).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(639157261, 250794925).append(this.protocoleNumber).append(
				this.fianceeHe).append(this.protocoleDate).append(
				this.assistanceDelegation).append(this.licenseAssistance)
				.append(this.id).append(this.certificate).append(
						this.fileNumber).append(this.dispensation).append(
						this.fianceeShe).append(this.marriageDate).append(
						this.marriageTime).append(this.place).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("licenseAssistance",
				this.licenseAssistance).append("fullNumber",
				this.getFullNumber()).append("fianceeHe", this.fianceeHe)
				.append("dispensation", this.dispensation).append("place",
						this.place).append("fianceeShe", this.fianceeShe)
				.append("marriageTime", this.marriageTime).append(
						"protocoleNumber", this.protocoleNumber).append(
						"assistanceDelegation", this.assistanceDelegation)
				.append("fileNumber", this.fileNumber).append("certificate",
						this.certificate).append("protocoleDate",
						this.protocoleDate).append("marriageDate",
						this.marriageDate).append("id", this.id).toString();
	}
}
