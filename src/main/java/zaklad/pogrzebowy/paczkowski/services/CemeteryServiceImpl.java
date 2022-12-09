package zaklad.pogrzebowy.paczkowski.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zaklad.pogrzebowy.paczkowski.domain.Cemetery;
import zaklad.pogrzebowy.paczkowski.domain.Team;
import zaklad.pogrzebowy.paczkowski.repo.CemeteryRepo;

@Service
public class CemeteryServiceImpl implements CemeteryService{

	@Autowired
	CemeteryRepo cemeteryRepo;
	
	@Override
	public void add(Cemetery c) {
		cemeteryRepo.save(c);
		
	}

	@Override
	public void del(Cemetery c) {
		cemeteryRepo.delete(c);
		
	}

	@Override
	public List<Cemetery> showAll() {
		List<Cemetery> res = cemeteryRepo.findAll();
		if(res == null)
			res = new ArrayList<>();
		return res;
	}

	@Override
	public Cemetery getByName(String name) {
		Optional<Cemetery> b = showAll().stream().filter(x -> x.getCemeteryName() == name).findFirst();
		if(b.isPresent())
			return b.get();
		return null;
	}

}
