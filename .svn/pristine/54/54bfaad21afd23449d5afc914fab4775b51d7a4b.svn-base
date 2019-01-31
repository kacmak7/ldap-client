package org.parafia.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity  
@DiscriminatorValue(value="0")
public class Niche extends GraveNiche {
	private static final long serialVersionUID = 6462486342113339076L;

	private Columbarium columbarium;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="columbarium_id", nullable=false)
	public Columbarium getColumbarium() {
		return columbarium;
	}
	
	public void setColumbarium(Columbarium columbarium) {
		this.columbarium = columbarium;
	}
}
