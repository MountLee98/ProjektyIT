package zaklad.pogrzebowy.paczkowski.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import zaklad.pogrzebowy.paczkowski.domain.Cemetery;


public interface CemeteryRepo extends JpaRepository<Cemetery, Long>{

}
