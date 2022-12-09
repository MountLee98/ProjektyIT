package zaklad.pogrzebowy.paczkowski.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Team {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfTheTeam() {
		return nameOfTheTeam;
	}

	public void setNameOfTheTeam(String nameOfTheTeam) {
		this.nameOfTheTeam = nameOfTheTeam;
	}

	@Id
	@GeneratedValue
	Long id;
	
	@Column(name="nameOfTheTeam")
	String nameOfTheTeam;
}
