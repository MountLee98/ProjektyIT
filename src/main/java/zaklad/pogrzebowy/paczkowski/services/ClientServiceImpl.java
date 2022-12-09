package zaklad.pogrzebowy.paczkowski.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zaklad.pogrzebowy.paczkowski.domain.Client;
import zaklad.pogrzebowy.paczkowski.repo.ClientRepo;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	ClientRepo clientRepo;
	
	@Override
	public void add(Client c) {
		clientRepo.save(c);
	}

	@Override
	public void del(Client c) {
		clientRepo.delete(c);
	}

	@Override
	public List<Client> showAll() {
		List<Client> res = clientRepo.findAll();
		if(res == null)
			res = new ArrayList<>();
		return res;
	}
	
}
