package org.parafia.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.parafia.util.MessageResources;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;

@Entity
@Table(name="intentions")
public class Intention extends CommonIntention {
	private static final long serialVersionUID = 6465312537193047694L;
	private static final String REPLACE_EOL = System.getProperty("line.separator");

	private Long id;
	
	@NotEmpty(message="{errors.value.notnull}")
	private Date date;
	
	@NotEmpty(message="{errors.value.notnull}")
	private String text;
	
	//@NotEmpty(message="{errors.value.notnull}") @Min(value=1, message="{errors.value.greaterthan} {value}")
	//@Pattern(regexp="\\d+([.,]\\d+)?", message="{errors.value.notnull}")
	private BigDecimal offering;
	
	private Set<Celebrant> celebrants = new HashSet<Celebrant>();
	
	private Set<Celebrant> confessions = new HashSet<Celebrant>();
	
	//@NotNull(message="{errors.value.notnull}")
	private Celebrant acceptor;
	
	@NotNull(message="{errors.value.notnull}")
	private IntentionType type;
	
	private IntentionTypeDuration duration;
	
	private String notices;
	
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
	
	@Column(nullable=true,length=1023)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Column(nullable=true)
	public BigDecimal getOffering() {
		return offering;
	}
	public void setOffering(BigDecimal offering) {
		this.offering = offering;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="accept_id", nullable=true)
	public Celebrant getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(Celebrant acceptor) {
		this.acceptor = acceptor;
	}
	
	@ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
            name="intentions_celebrants",
            joinColumns = { @JoinColumn( name="intention_id") },
            inverseJoinColumns = @JoinColumn( name="celebrant_id")
    )    
    public Set<Celebrant> getCelebrants() {
        return celebrants;
    }
	
    public void setCelebrants(Set<Celebrant> celebrants) {
		this.celebrants = celebrants;
	}
   
	@ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
            name="intentions_confessions",
            joinColumns = { @JoinColumn( name="intention_id") },
            inverseJoinColumns = @JoinColumn( name="celebrant_id")
    )    
    public Set<Celebrant> getConfessions() {
        return confessions;
    }
    
    public void setConfessions(Set<Celebrant> confessions) {
		this.confessions = confessions;
	}
    
    @ManyToOne(optional=false)
	@JoinColumn(name="type_id", nullable=false)
	public IntentionType getType() {
		return type;
	}
	public void setType(IntentionType type) {
		this.type = type;
	}
	
    @ManyToOne(optional=true)
	@JoinColumn(name="duration_id")
	public IntentionTypeDuration getDuration() {
		return duration;
	}
	public void setDuration(IntentionTypeDuration duration) {
		this.duration = duration;
	}
	
	@Column(nullable=true)
	public String getNotices() {
		return notices;
	}
	public void setNotices(String notices) {
		this.notices = notices;
	}
    
	/**
     * Convert user roles to LabelValue objects for convenience.
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getCelebrantList() {
        List<LabelValue> intentionCelebrants = new ArrayList<LabelValue>();

        if (this.celebrants != null) {
            for (Celebrant c : celebrants) {
                // convert the user's roles to LabelValue Objects
            	intentionCelebrants.add(new LabelValue(c.getFullName(), String.valueOf(c.getId())));
            }
        }

        return intentionCelebrants;
    }
    
	/**
     * Convert user roles to LabelValue objects for convenience.
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getConfessionList() {
        List<LabelValue> intentionConfessions = new ArrayList<LabelValue>();

        if (this.confessions != null) {
            for (Celebrant c : confessions) {
                // convert the user's roles to LabelValue Objects
            	intentionConfessions.add(new LabelValue(c.getFullName(), String.valueOf(c.getId())));
            }
        }

        return intentionConfessions;
    }
    
    @Transient
    public String getCelebrantsString() {
    	String str = "";
    	for (Celebrant c : getCelebrants()) {
    		str += c.getFullName() + "\n";
    	}
    	if (str.length() > 1)
    		return str.substring(0, str.length() - 1);
    	else
    		return str;
    }
    
    @Transient
    public String getConfessionsString() {
    	String str = "";
    	for (Celebrant c : getConfessions()) {
    		str += c.getFullName() + "\n";
    	}
    	if (str.length() > 1)
    		return str.substring(0, str.length() - 1);
    	else
    		return str;
    }
    
    @Transient
    public String getInfo() {
    	String str = "";
    	
    	if (getCelebrants().size() > 0) {
	    	str += REPLACE_EOL + REPLACE_EOL + MessageResources.getMessage("tomes.editIntention.celebrants");
	    	for (Celebrant c : getCelebrants()) {
	    		str += REPLACE_EOL + c.getFullName();
	    	}
    	}
    	
    	if (getConfessions().size() > 0) {
	    	str += REPLACE_EOL + REPLACE_EOL + MessageResources.getMessage("tomes.editIntention.confessions");
	    	for (Celebrant c : getConfessions()) {
	    		str += REPLACE_EOL + c.getFullName();
	    	}
    	}
    	return str;
    }
	
	@Override
	public String toString() {
		return "Intention [id=" + id + ", date=" + date + ", text=" + text + ", offering="
				+ offering + ", celebrants=" + celebrants + ", confessions=" + confessions + "]";
	}

	public String toString(DateFormat df) {
		return "{\"title\": \""+replaceEols(text)+"\",\"start\": \""+df.format(getDate()) + "\",\"color\": \""+getType().getColor() + "\",\"info\": \""+getInfo() + "\"}";
	}
	
	@Override
	public Intention clone() {
		Intention newInt = new Intention();
		newInt.setAcceptor(acceptor);
		newInt.setCelebrants(celebrants);
		newInt.setConfessions(confessions);
		newInt.setDate(date);
		newInt.setOffering(offering);
		newInt.setText(text);
		newInt.setToDate(getToDate());
		newInt.setType(type);
		newInt.setDuration(duration);
		newInt.setNotices(notices);
		
		return newInt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceptor == null) ? 0 : acceptor.hashCode());
		result = prime * result + ((celebrants == null) ? 0 : celebrants.hashCode());
		result = prime * result + ((confessions == null) ? 0 : confessions.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notices == null) ? 0 : notices.hashCode());
		result = prime * result + ((offering == null) ? 0 : offering.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
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
		Intention other = (Intention) obj;
		if (acceptor == null) {
			if (other.acceptor != null)
				return false;
		} else if (!acceptor.equals(other.acceptor))
			return false;
		if (celebrants == null) {
			if (other.celebrants != null)
				return false;
		} else if (!celebrants.equals(other.celebrants))
			return false;
		if (confessions == null) {
			if (other.confessions != null)
				return false;
		} else if (!confessions.equals(other.confessions))
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
		if (notices == null) {
			if (other.notices != null)
				return false;
		} else if (!notices.equals(other.notices))
			return false;
		if (offering == null) {
			if (other.offering != null)
				return false;
		} else if (!offering.equals(other.offering))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		return true;
	}
}
