package org.parafia.model.printcards;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.parafia.model.BaseObject;

@Entity
@Table(name="remainings")
public class Remaining extends BaseObject {
	private static final long serialVersionUID = 4629944788060633643L;

	private Long id;
	
	private String mothersFirstName;
	private String mothersLastName;
	private String mothersMaidensName;
	private String fathersFirstName;
	private String fathersLastName;
	private String birthPlace;
	
	private Baptism baptism;
	private Confirmation confirmation;
	private Marriage marriage;
	private Death death;
	
	private BaptismChanges baptismChanges;
	
	private BaptismKnowAct baptismKnowAct;
	
	private BaptismPreparation baptismPreparation;
	
	//private boolean isWoman;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="mothers_first_name", length=25)
	public String getMothersFirstName() {
		return mothersFirstName;
	}
	public void setMothersFirstName(String mothersFirstName) {
		this.mothersFirstName = mothersFirstName;
	}

	@Column(name="mothers_last_name", length=25)
	public String getMothersLastName() {
		return mothersLastName;
	}
	public void setMothersLastName(String mothersLastName) {
		this.mothersLastName = mothersLastName;
	}

	@Column(name="mothers_maidens_name", length=25)
	public String getMothersMaidensName() {
		return mothersMaidensName;
	}
	public void setMothersMaidensName(String mothersMaidensName) {
		this.mothersMaidensName = mothersMaidensName;
	}

	@Column(name="fathers_first_name", length=25)
	public String getFathersFirstName() {
		return fathersFirstName;
	}
	public void setFathersFirstName(String fathersFirstName) {
		this.fathersFirstName = fathersFirstName;
	}

	@Column(name="fathers_last_name", length=25)
	public String getFathersLastName() {
		return fathersLastName;
	}
	public void setFathersLastName(String fathersLastName) {
		this.fathersLastName = fathersLastName;
	}

	@Column(name="birth_place", length=25)
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Baptism getBaptism() {
		return baptism;
	}
	public void setBaptism(Baptism baptism) {
		this.baptism = baptism;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Confirmation getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(Confirmation confirmation) {
		this.confirmation = confirmation;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Death getDeath() {
		return death;
	}
	public void setDeath(Death death) {
		this.death = death;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Marriage getMarriage() {
		return marriage;
	}
	public void setMarriage(Marriage marriage) {
		this.marriage = marriage;
	}

	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public BaptismChanges getBaptismChanges() {
		return baptismChanges;
	}
	public void setBaptismChanges(BaptismChanges baptismChanges) {
		this.baptismChanges = baptismChanges;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public BaptismKnowAct getBaptismKnowAct() {
		return baptismKnowAct;
	}
	public void setBaptismKnowAct(BaptismKnowAct baptismKnowAct) {
		this.baptismKnowAct = baptismKnowAct;
	}
	
	@OneToOne
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public BaptismPreparation getBaptismPreparation() {
		return baptismPreparation;
	}
	public void setBaptismPreparation(BaptismPreparation baptismPreparation) {
		this.baptismPreparation = baptismPreparation;
	}
	
	@Transient
	public String getFathersFullName() {
		if (fathersFirstName != null && fathersLastName != null)
			return fathersFirstName + " " + fathersLastName;
		else
			return null;
	}
	
	@Transient
	public String getMothersFullName() {
		if (mothersFirstName != null && mothersLastName != null) {
			if (!"".equals(mothersLastName))
				return mothersFirstName + " " + mothersLastName;
			else
				return mothersFirstName;
		} else
			return null;
	}
	
	@Transient
	public String getMothersMaidenFullName() {
		if (StringUtils.isNotBlank(mothersMaidensName)) {
			return mothersFirstName + " " + mothersMaidensName;
		} else if (StringUtils.isNotBlank(mothersLastName)) {
			return mothersFirstName + " " + mothersLastName;
		} else if (StringUtils.isNotBlank(mothersFirstName)) {
			return mothersFirstName;
		}
		return null;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Remaining)) {
			return false;
		}
		Remaining rhs = (Remaining) object;
		return new EqualsBuilder().append(
				this.id, rhs.id)
				.append(this.baptismKnowAct, rhs.baptismKnowAct).append(
						this.fathersFirstName, rhs.fathersFirstName).append(
						this.mothersFirstName, rhs.mothersFirstName).append(
						this.baptismPreparation, rhs.baptismPreparation)
				.append(this.fathersLastName, rhs.fathersLastName).append(
						this.mothersLastName, rhs.mothersLastName).append(
						this.mothersMaidensName, rhs.mothersMaidensName)
				.append(this.birthPlace, rhs.birthPlace).append(
						this.baptismChanges, rhs.baptismChanges).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1357760333, -497968619).append(this.id).append(this.baptismKnowAct)
				.append(this.fathersFirstName).append(this.mothersFirstName)
				.append(this.baptismPreparation).append(this.fathersLastName)
				.append(this.mothersLastName).append(this.mothersMaidensName)
				.append(this.birthPlace).append(this.baptismChanges)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("fathersLastName",
				this.fathersLastName).append("birthPlace", this.birthPlace)
				.append("fathersFirstName", this.fathersFirstName).append(
						"baptismKnowAct", this.baptismKnowAct).append(
						"mothersFullName", this.getMothersFullName()).append(
						"mothersMaidensName", this.mothersMaidensName).append(
						"baptismChanges", this.baptismChanges).append(
						"baptismPreparation", this.baptismPreparation).append(
						"mothersFirstName", this.mothersFirstName).append(
						"mothersLastName", this.mothersLastName).append(
						"fathersFullName", this.getFathersFullName()).append(
						"id", this.id).toString();
	}
}
