package org.parafia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.parafia.Constants;

@Entity
@Table(name="investigation_protocole")
public class InvestigationProtocole extends BaseObject {
	private static final long serialVersionUID = -5103779879197638784L;

	private Long id;
	private String[] answers = new String[Integer.valueOf(Constants.NUMBER_OF_QUESTIONS)];
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String[] getAnswers() {
		return answers;
	}
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InvestigationProtocole)) {
			return false;
		}
		InvestigationProtocole rhs = (InvestigationProtocole) object;
		return new EqualsBuilder().append(
				this.id, rhs.id).append(this.answers, rhs.answers).isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-325262949, -175437973).append(this.id).append(this.answers)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("answers", this.answers)
				.append("id", this.id).toString();
	}
}
