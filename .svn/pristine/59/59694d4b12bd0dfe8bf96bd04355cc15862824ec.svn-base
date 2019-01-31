package org.parafia.model;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;

@Entity
@Table(name="intentions_annotations")
public class IntentionAnnotation extends CommonIntention {
	private static final long serialVersionUID = -3156159348463721080L;

	private Long id;
	
	@NotEmpty
	private Date date;
	
	@NotEmpty
	private String annotation;
	
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

	@Column(nullable=false,length=1023)
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annotation == null) ? 0 : annotation.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntentionAnnotation other = (IntentionAnnotation) obj;
		if (annotation == null) {
			if (other.annotation != null)
				return false;
		} else if (!annotation.equals(other.annotation))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IntentionAnnotation [id=" + id + ", date=" + date + ", annotation=" + annotation + "]";
	}
	
	@Override
	public IntentionAnnotation clone() {
		IntentionAnnotation ia = new IntentionAnnotation();
		ia.setAnnotation(annotation);
		ia.setDate(date);
		ia.setToDate(getToDate());
		return ia;
	}
	
	public String toString(DateFormat df) {
		return "{\"title\": \""+replaceEols(annotation)+"\",\"start\": \""+df.format(getDate()) + "\",\"allDay\":true,\"id\":"+getId()+"}";
	}
}
