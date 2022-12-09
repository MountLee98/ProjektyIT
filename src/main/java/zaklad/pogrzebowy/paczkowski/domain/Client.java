package zaklad.pogrzebowy.paczkowski.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Client {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CauseOfDeath getDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(CauseOfDeath death) {
		this.causeOfDeath = death;
	}

	@Id
	@GeneratedValue
	Long id;
	
	String name;
	
	CauseOfDeath causeOfDeath;

}
