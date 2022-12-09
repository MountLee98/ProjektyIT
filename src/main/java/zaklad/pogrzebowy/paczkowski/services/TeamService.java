package zaklad.pogrzebowy.paczkowski.services;

import java.util.List;

import zaklad.pogrzebowy.paczkowski.domain.Team;


public interface TeamService {
	
	void add(Team t);
	void del(Team t);

	List<Team> showAll();
	
	Team getByName(String name);
	
}
