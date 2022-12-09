package zaklad.pogrzebowy.paczkowski.services;

import java.util.List;

import zaklad.pogrzebowy.paczkowski.domain.Client;

public interface ClientService {
	
	void add(Client c);
	void del(Client c);

	List<Client> showAll();
}
