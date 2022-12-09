package zaklad.pogrzebowy.paczkowski.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FuneralDeadline {

	@Id
	@GeneratedValue
	Long id;
	
	@Column(name="nameOfDeaceased")
	String nameOfDeceased;
	
	@Column(name="causeOfDeath")
	CauseOfDeath causeOfDeath;
	
	@Column(name="cemeteryName")
	String cemeteryName;
	
	public String getCemeteryName() {
		return cemeteryName;
	}

	public void setCemeteryName(String cemeteryName) {
		this.cemeteryName = cemeteryName;
	}

	@Column(name="nameOfTeam")
	String nameOfTeam;
	
	LocalDateTime deadline;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfDeceased() {
		return nameOfDeceased;
	}

	public void setNameOfDeceased(String nameOfDeceased) {
		this.nameOfDeceased = nameOfDeceased;
	}

	public CauseOfDeath getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(CauseOfDeath causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}

	public String getNameOfTeam() {
		return nameOfTeam;
	}

	public void setNameOfTeam(String nameOfTeam) {
		this.nameOfTeam = nameOfTeam;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
}
