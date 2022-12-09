package zaklad.pogrzebowy.paczkowski.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cemetery {

	@Id
	@GeneratedValue
	Long id;
	
	@Column(name="cemeteryName")
	String cemeteryName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCemeteryName() {
		return cemeteryName;
	}

	public void setCemeteryName(String cemetery_name) {
		this.cemeteryName = cemetery_name;
	}



}
