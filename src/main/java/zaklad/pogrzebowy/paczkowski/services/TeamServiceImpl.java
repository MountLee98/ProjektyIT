package zaklad.pogrzebowy.paczkowski.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zaklad.pogrzebowy.paczkowski.domain.Team;
import zaklad.pogrzebowy.paczkowski.repo.TeamRepo;

@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	TeamRepo teamRepo;
	@Override
	public void add(Team t) {
		teamRepo.save(t);
		
	}

	@Override
	public void del(Team t) {
		teamRepo.delete(t);
		
	}

	@Override
	public List<Team> showAll() {
		List<Team> res = teamRepo.findAll();
		if(res == null)
			res = new ArrayList<>();
		return res;
	}

	@Override
	public Team getByName(String name) {
		Optional<Team> b = showAll().stream().filter(x -> x.getNameOfTheTeam() == name).findFirst();
		if(b.isPresent())
			return b.get();
		return null;
	}

}
