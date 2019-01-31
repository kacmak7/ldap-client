package org.parafia.webapp.controller.commands;

import java.io.Serializable;
import java.math.BigDecimal;

import org.parafia.model.IntentionType;
import org.springframework.stereotype.Component;

public class OfferingSumIntentions implements Serializable{

	private static final long serialVersionUID = 5941882321958570464L;
	
	private IntentionType intentionType;
	private BigDecimal sum;

	public IntentionType getIntentionType() {
		return intentionType;
	}

	public void setIntentionType(IntentionType intentionType) {
		this.intentionType = intentionType;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "TypeSumIntentions [intentionType=" + intentionType + ", sum=" + sum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intentionType == null) ? 0 : intentionType.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
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
		OfferingSumIntentions other = (OfferingSumIntentions) obj;
		if (intentionType == null) {
			if (other.intentionType != null)
				return false;
		} else if (!intentionType.equals(other.intentionType))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		return true;
	}

}
