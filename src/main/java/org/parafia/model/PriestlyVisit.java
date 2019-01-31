package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="priestly_visits", uniqueConstraints={@UniqueConstraint(columnNames={"family_id", "date", "remarks"})})
public class PriestlyVisit extends BaseObject {
	private static final long serialVersionUID = -5673222138793993778L;

	private Long id;
	private Family family;
	private String date;
	private String remarks;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="family_id", nullable=false)
	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	
	@Column(nullable=false, length=10)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Column(nullable=true)
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PriestlyVisit)) {
			return false;
		}
		PriestlyVisit rhs = (PriestlyVisit) object;
		return new EqualsBuilder().append(
				this.family, rhs.family).append(
				this.remarks, rhs.remarks).append(this.date,
				rhs.date).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(228273711, -947555905).append(this.id).append(this.family).append(
				this.remarks).append(this.date).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("date", this.date).append(
				"priestRemarks", this.remarks).append("family",
				this.family).append("id", this.id).toString();
	}
	
	/*public int compareTo(PriestlyVisit o) {
		if (date == null && o.date == null)
			return 0;
		else if (o.date == null && date != null)
        	return 1;
        else if (o.date != null && date == null)
        	return -1;
        else {
        	int compare = date.substring(date.length() - 4, date.length())
        		.compareTo(o.date.substring(o.date.length() - 4, o.date.length()));
        	if (compare != 0)
        		return compare;
        	else {
        		int comp = date.compareTo(o.date);
        		if (comp != 0)
        			return comp;
        		else if (remarks != null)
        			return remarks.compareTo(o.remarks);
        		else if (o.remarks != null)
        			return -1;
        		else
        			return 0;
        	}
        }
	}
	
	public static class Comparator implements java.util.Comparator<PriestlyVisit> {
        public int compare(PriestlyVisit a, PriestlyVisit b) {
            if (a.date == null)
            	return -1;
            else if (b.date == null)
            	return 1;
            else {
            	int compare = a.date.substring(a.date.length() - 4, a.date.length())
            		.compareTo(b.date.substring(b.date.length() - 4, b.date.length()));
            	if (compare != 0)
            		return compare;
            	else {
            		int comp = a.date.compareTo(b.date);
            		if (comp != 0)
            			return comp;
            		else if (a.remarks != null)
            			return a.remarks.compareTo(b.remarks);
            		else if (b.remarks != null)
            			return -1;
            		else
            			return 0;
            	}
            }
        }
    }*/
}
