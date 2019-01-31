package org.parafia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.parafia.dao.hibernate.FamilyDaoHibernate;
import org.parafia.model.dictionary.MarriageStatus;

@Entity
@Table(name = "families")
public class Family extends BaseObject {
	private static final long serialVersionUID = 7468736902964829306L;
	private Long id;
	private Address address;
	private String phone;
	private MarriageStatus marriageStatus;
	private String priestRemarks;
	private String materialSituation;
	private Parent husband = null;
	private Parent wife = null;

	private List<Child> children = new ArrayList<Child>();
	private List<Other> others = new ArrayList<Other>();
	private List<PriestlyVisit> visits = new ArrayList<PriestlyVisit>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", nullable = true)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(nullable = true, length = 25)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@OneToOne(optional=true)
	@JoinColumn(name="husband_id", nullable=true)
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Parent getHusband() {
		return husband;
	}

	public void setHusband(Parent husband) {
		this.husband = husband;
	}

	@OneToOne(optional=true)
	@JoinColumn(name="wife_id", nullable=true)
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	public Parent getWife() {
		return wife;
	}

	public void setWife(Parent wife) {
		this.wife = wife;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "marriage_status_id", nullable = false)
	public MarriageStatus getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(MarriageStatus marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	@Column(name = "priest_remarks", nullable = true, length = 512)
	public String getPriestRemarks() {
		return priestRemarks;
	}

	public void setPriestRemarks(String priestRemarks) {
		this.priestRemarks = priestRemarks;
	}

	@Column(name = "material_situation", nullable = true, length = 512)
	public String getMaterialSituation() {
		return materialSituation;
	}

	public void setMaterialSituation(String materialSituation) {
		this.materialSituation = materialSituation;
	}

	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="family")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<Other> getOthers() {
		return others;
	}

	public void setOthers(List<Other> others) {
		this.others = others;
	}

	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="family")
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "family")	//TODO: is that correct?
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<PriestlyVisit> getVisits() {
		return visits;
	}

	public void setVisits(List<PriestlyVisit> visits) {
		this.visits = visits;
	}

	@Transient
	public List<PriestlyVisit> getVisitsSorted() {
		Collections.sort(visits, new Comparator());
		return visits;
	}

	@Transient
	public String getSurname() {
		String out = "";
		if (husband != null)
			out = husband.getSurname();
		else if(wife != null)
			out = wife.getSurname();
		return out;
	}

	@Transient
	public List<Person> getAllPersons() {
		List<Person> persons = new ArrayList<Person>();
		if (husband != null)
			persons.add(husband);
		if (wife != null)
			persons.add(wife);
		persons.addAll(children);
		persons.addAll(others);
		return persons;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Family)) {
			return false;
		}
		Family rhs = (Family) object;
		return new EqualsBuilder().append(this.wife, rhs.wife).append(this.husband, rhs.husband)
				.append(this.phone, rhs.phone).append(this.others, rhs.others)
				.append(this.marriageStatus, rhs.marriageStatus).append(this.address, rhs.address)
				.append(this.priestRemarks, rhs.priestRemarks).append(this.materialSituation, rhs.materialSituation)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-205962779, -565634533).append(this.wife).append(this.husband).append(this.phone)
				.append(this.others).append(this.marriageStatus).append(this.address).append(this.priestRemarks)
				.append(this.materialSituation).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("status", this.marriageStatus).append("others", this.others)
				.append("materialSituation", this.materialSituation).append("phone", this.phone)
				.append("priestRemarks", this.priestRemarks).append("parents", this.wife).append(this.husband)
				.append("address", this.address).append("id", this.id).toString();
	}

	public static class Comparator implements java.util.Comparator<PriestlyVisit> {
		public int compare(PriestlyVisit a, PriestlyVisit b) {
			if (a.getDate() == null)
				return -1;
			else if (b.getDate() == null)
				return 1;
			else {
				int compare = a.getDate().substring(a.getDate().length() - 4, a.getDate().length())
						.compareTo(b.getDate().substring(b.getDate().length() - 4, b.getDate().length()));
				if (compare != 0)
					return compare;
				else {
					int comp = a.getDate().compareTo(b.getDate());
					if (comp != 0)
						return comp;
					else if (a.getRemarks() != null)
						return a.getRemarks().compareTo(b.getRemarks());
					else if (b.getRemarks() != null)
						return -1;
					else
						return 0;
				}
			}
		}
	}
}
