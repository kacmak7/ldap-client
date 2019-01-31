package org.parafia.webapp.controller.commands;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Embeddable;

import org.parafia.model.Celebrant;
import org.parafia.model.Intention;
import org.parafia.model.IntentionType;
import org.parafia.service.IntentionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class CelebrantIntentions implements Serializable {

	private static final long serialVersionUID = -7204060534816480962L;

	private Celebrant celebrant;
	private List<Intention> intentions;
	private List<OfferingSumIntentions> sumIntentions;

	public Celebrant getCelebrant() {
		return celebrant;
	}

	public void setCelebrant(Celebrant celebrant) {
		this.celebrant = celebrant;
	}

	public List<Intention> getIntentions() {
		return intentions;
	}

	public void setIntentions(List<Intention> intentions) {
		this.intentions = intentions;
	}

	public List<OfferingSumIntentions> getSumIntentions() {
		return sumIntentions;
	}

	public void setSumIntentions(List<OfferingSumIntentions> sumIntentions) {
		this.sumIntentions = sumIntentions;
	}

	@Override
	public String toString() {
		return "CelebrantIntentions [celebrant=" + celebrant + ", intentions=" + intentions + ", sumIntentions="
				+ sumIntentions + ", valueFromIntentions=" + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celebrant == null) ? 0 : celebrant.hashCode());
		result = prime * result + ((intentions == null) ? 0 : intentions.hashCode());
		result = prime * result + ((sumIntentions == null) ? 0 : sumIntentions.hashCode());
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
		CelebrantIntentions other = (CelebrantIntentions) obj;
		if (celebrant == null) {
			if (other.celebrant != null)
				return false;
		} else if (!celebrant.equals(other.celebrant))
			return false;
		if (intentions == null) {
			if (other.intentions != null)
				return false;
		} else if (!intentions.equals(other.intentions))
			return false;
		if (sumIntentions == null) {
			if (other.sumIntentions != null)
				return false;
		} else if (!sumIntentions.equals(other.sumIntentions))
			return false;
		return true;
	}

}