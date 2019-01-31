package org.parafia.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GravePoint extends BaseObject {
	private static final long serialVersionUID = -764615755159523336L;

	private Long id;
	
	private double x;
	private double y;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GravePoint)) {
			return false;
		}
		GravePoint rhs = (GravePoint) object;
		return new EqualsBuilder().append(this.y, rhs.y).append(this.x, rhs.x)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1007362853, -2140019601).append(this.y).append(this.x)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("y", this.y)
				.append("x", this.x).append("id", this.id).toString();
	}
}
