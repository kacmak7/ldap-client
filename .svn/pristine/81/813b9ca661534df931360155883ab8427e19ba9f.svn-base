package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="grave_levels")
public class GraveLevel extends BaseObject {
	private static final long serialVersionUID = -3954513853117352090L;

	private Long id;
	
	private Grave grave;
	private int levelId;
	private int placesNumber;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="grave_id", nullable=false)
	public Grave getGrave() {
		return grave;
	}
	public void setGrave(Grave grave) {
		this.grave = grave;
	}
	
	@Column(name="level_id")
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	
	@Column(name="places_number")
	public int getPlacesNumber() {
		return placesNumber;
	}
	public void setPlacesNumber(int placesNumber) {
		this.placesNumber = placesNumber;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GraveLevel)) {
			return false;
		}
		GraveLevel rhs = (GraveLevel) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.levelId, rhs.levelId).append(
				this.placesNumber, rhs.placesNumber).append(this.grave,
				rhs.grave).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-858641291, -1542448819).append(this.id).append(this.levelId).append(
				this.placesNumber).append(this.grave).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("levelId", this.levelId)
				.append("grave", this.grave).append("placesNumber",
						this.placesNumber).append("id", this.id).toString();
	}
}
