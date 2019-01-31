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
@Table(name="grave_files")
public class GraveFile extends BaseObject {
	private static final long serialVersionUID = -1389403293757122078L;

	private Long id;
	private String contentType;
	private String path;
	private String originalName;
	private GraveNiche grave;
	private long size;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="content_type")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Column
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name="original_name")
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="grave_id", nullable=false)
	public GraveNiche getGrave() {
		return grave;
	}
	public void setGrave(GraveNiche grave) {
		this.grave = grave;
	}
	
	@Column
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GraveFile)) {
			return false;
		}
		GraveFile rhs = (GraveFile) object;
		return new EqualsBuilder().append(this.size, rhs.size).append(
				this.path, rhs.path)
				.append(this.originalName, rhs.originalName).append(this.grave.getId(),
						rhs.grave.getId()).append(this.contentType, rhs.contentType)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(346034061, 1603268285).append(this.size).append(
				this.path).append(this.originalName).append(this.grave.getId()).append(
				this.contentType).toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("grave", this.grave).append(
				"contentType", this.contentType).append("originalName",
				this.originalName).append("path", this.path).append("size",
				this.size).append("id", this.id).toString();
	}
}
