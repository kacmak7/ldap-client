package org.parafia.model.dictionary;

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
@Table(name="baptism_places")
public class BaptismPlace extends BaseObject {
	private static final long serialVersionUID = -5224546400548005349L;

	private Long id;
	private String name;
	private String secondName;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="second_name", nullable=false, unique=true)
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BaptismPlace)) {
			return false;
		}
		BaptismPlace rhs = (BaptismPlace) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.secondName, rhs.secondName)
				.append(this.name, rhs.name).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(779923555, -1460263593).append(this.id).append(this.secondName)
				.append(this.name).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("secondName", this.secondName)
				.append("name", this.name).append("id", this.id).toString();
	}
}
