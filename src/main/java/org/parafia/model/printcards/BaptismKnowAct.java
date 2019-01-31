package org.parafia.model.printcards;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.parafia.model.BaseObject;
import org.parafia.model.dictionary.BaptismPlace;

@Entity
@Table(name="baptism_know_acts")
/**
 * Akt znania o chrzie
 */
public class BaptismKnowAct extends BaseObject {
	private static final long serialVersionUID = -3025096880813877026L;

	private Long id;
	
	private Date date;
	private String priestFirstName;
	private String priestSurname;
	private String acquaintanceFrom;
	private String relationship;
	private BaptismPlace baptismPlace;
	private String baptismPlaceAdditional;
	private String baptismGiver;
	private String godfather;
	private String godmother;
	private String baptismKnow;
	private String reasonNotAct;
	private String additional;
	
	private BaptismWitness witness;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="priest_first_name", nullable=false, length=25)
	public String getPriestFirstName() {
		return priestFirstName;
	}
	public void setPriestFirstName(String priestFirstName) {
		this.priestFirstName = priestFirstName;
	}
	
	@Column(name="priest_surname", nullable=false, length=25)
	public String getPriestSurname() {
		return priestSurname;
	}
	public void setPriestSurname(String priestSurname) {
		this.priestSurname = priestSurname;
	}
	
	@Column(name="acquaintance_from", nullable=false)
	public String getAcquaintanceFrom() {
		return acquaintanceFrom;
	}
	public void setAcquaintanceFrom(String acquaintanceFrom) {
		this.acquaintanceFrom = acquaintanceFrom;
	}

	@Column(nullable=false)
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	@Column(nullable=false, name="baptism_place")
	public BaptismPlace getBaptismPlace() {
		return baptismPlace;
	}
	public void setBaptismPlace(BaptismPlace baptismPlace) {
		this.baptismPlace = baptismPlace;
	}
	
	@Column(nullable=false, name="baptism_place_additionals")
	public String getBaptismPlaceAdditional() {
		return baptismPlaceAdditional;
	}
	public void setBaptismPlaceAdditional(String baptismPlaceAdditional) {
		this.baptismPlaceAdditional = baptismPlaceAdditional;
	}

	@Column(nullable=false, name="baptism_giver")
	public String getBaptismGiver() {
		return baptismGiver;
	}
	public void setBaptismGiver(String baptismGiver) {
		this.baptismGiver = baptismGiver;
	}

	@Column(nullable=false)
	public String getGodfather() {
		return godfather;
	}
	public void setGodfather(String godfather) {
		this.godfather = godfather;
	}

	@Column(nullable=false)
	public String getGodmother() {
		return godmother;
	}
	public void setGodmother(String godmother) {
		this.godmother = godmother;
	}

	@Column(nullable=false, name="baptism_know")
	public String getBaptismKnow() {
		return baptismKnow;
	}
	public void setBaptismKnow(String baptismKnow) {
		this.baptismKnow = baptismKnow;
	}

	@Column(nullable=false, name="reason_not_act")
	public String getReasonNotAct() {
		return reasonNotAct;
	}
	public void setReasonNotAct(String reasonNotAct) {
		this.reasonNotAct = reasonNotAct;
	}

	@Column(nullable=true)
	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	@Embedded
	public BaptismWitness getWitness() {
		return witness;
	}

	public void setWitness(BaptismWitness witness) {
		this.witness = witness;
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
		if (!(object instanceof BaptismKnowAct)) {
			return false;
		}
		BaptismKnowAct rhs = (BaptismKnowAct) object;
		return new EqualsBuilder().append(
				this.reasonNotAct, rhs.reasonNotAct).append(this.priestSurname,
				rhs.priestSurname).append(this.baptismGiver, rhs.baptismGiver)
				.append(this.baptismPlace, rhs.baptismPlace).append(
						this.godfather, rhs.godfather).append(this.witness,
						rhs.witness).append(this.id, rhs.id).append(
						this.relationship, rhs.relationship).append(
						this.baptismKnow, rhs.baptismKnow).append(
						this.additional, rhs.additional).append(this.godmother,
						rhs.godmother).append(this.acquaintanceFrom,
						rhs.acquaintanceFrom).append(this.priestFirstName,
						rhs.priestFirstName).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(719859665, -2116163019).append(this.reasonNotAct).append(
				this.priestSurname).append(this.baptismGiver).append(
				this.baptismPlace).append(this.godfather).append(this.witness)
				.append(this.id).append(this.relationship).append(
						this.baptismKnow).append(this.additional).append(
						this.godmother).append(this.acquaintanceFrom).append(
						this.priestFirstName).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("baptismGiver",
				this.baptismGiver).append("additional", this.additional)
				.append("acquaintanceFrom", this.acquaintanceFrom).append(
						"godmother", this.godmother).append("priestFirstName",
						this.priestFirstName).append("baptismKnow",
						this.baptismKnow).append("priestSurname",
						this.priestSurname).append("relationship",
						this.relationship).append("baptismPlace",
						this.baptismPlace).append("godfather", this.godfather)
				.append("witness", this.witness).append("id", this.id).append(
						"reasonNotAct", this.reasonNotAct).toString();
	}
}
