package zaklad.pogrzebowy.paczkowski.services;

import java.util.List;

import zaklad.pogrzebowy.paczkowski.domain.Cemetery;


public interface CemeteryService {
	
	void add(Cemetery c);
	void del(Cemetery c);

	List<Cemetery> showAll();
	
	Cemetery getByName(String name);
}
