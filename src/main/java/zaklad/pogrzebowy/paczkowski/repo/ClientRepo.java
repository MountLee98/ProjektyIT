package zaklad.pogrzebowy.paczkowski.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import zaklad.pogrzebowy.paczkowski.domain.Client;

public interface ClientRepo extends JpaRepository<Client, Long>{

}
