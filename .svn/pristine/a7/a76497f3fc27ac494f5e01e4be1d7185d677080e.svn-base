package org.parafia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.parafia.model.dictionary.GraveOwned;

@Entity
@Table(name="graves")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.INTEGER)
public abstract class GraveNiche extends BaseObject {
	private static final long serialVersionUID = -7315539249345329993L;

	private Long id;
	
	private GraveOwner owner = new GraveOwner();
	private int validTo;									//rok, do ktorego jest wazny grob - ustalone, ze tylko rok
	private GraveOwned owned = new GraveOwned();			//nisza wlasnosciowa jesli 1, pokladna jesli 0
	
	private int number;										//numer niszy
	private String notices;									//dodatkowe notatki
	//private String postgisString;							//geometry w postaci POLYGON(X Y, X1 Y1, ...)
	
	private List<GravePerson> persons = new ArrayList<GravePerson>();
	//private List<GraveLevel> levels = new ArrayList<GraveLevel>();
	
	//private List<GravePoint> points = new ArrayList<GravePoint>();
	private List<GraveFile> files = new ArrayList<GraveFile>();
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, unique=true)
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	@Embedded
	public GraveOwner getOwner() {
		return owner;
	}
	public void setOwner(GraveOwner owner) {
		this.owner = owner;
	}
	
	@Column(name="valid_to")
	public int getValidTo() {
		return validTo;
	}
	public void setValidTo(int validTo) {
		this.validTo = validTo;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="grave_owned_id", nullable=false)
	public GraveOwned getOwned() {
		return owned;
	}
	public void setOwned(GraveOwned owned) {
		this.owned = owned;
	}
	
	@Column(length=1023)
	public String getNotices() {
		return notices;
	}
	public void setNotices(String notices) {
		this.notices = notices;
	}

	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="grave")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<GravePerson> getPersons() {
		return persons;
	}
	public void setPersons(List<GravePerson> nichePersons) {
		this.persons = nichePersons;
	}
	
	@Transient
	public String getPersonsHtml() {
		if (persons != null) {
			String ret = ""; 
			for (GravePerson person : persons) {
				ret += person.getFirstName() + " " + person.getSurname() + "\n";
			}
			return ret;
		} else
			return null;
	}
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="grave")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<GraveFile> getFiles() {
		return files;
	}
	public void setFiles(List<GraveFile> files) {
		this.files = files;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notices == null) ? 0 : notices.hashCode());
		result = prime * result + number;
		result = prime * result + ((owned == null) ? 0 : owned.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		result = prime * result + validTo;
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
		GraveNiche other = (GraveNiche) obj;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notices == null) {
			if (other.notices != null)
				return false;
		} else if (!notices.equals(other.notices))
			return false;
		if (number != other.number)
			return false;
		if (owned == null) {
			if (other.owned != null)
				return false;
		} else if (!owned.equals(other.owned))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		if (validTo != other.validTo)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Niche [id=" + id + ", owner=" + owner + ", validTo=" + validTo
				+ ", owned=" + owned + ", number="
				+ number + ", notices=" + notices /*+ ", persons="
				+ persons + ", files=" + files*/ + "]";
	}
	
	public int compareTo(GraveNiche graveNiche) {
		return this.number - graveNiche.number;
	}
}
