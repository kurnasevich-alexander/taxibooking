package eu.senla.taxibooking.repository;

import eu.senla.taxibooking.entity.Locality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalityRepository extends JpaRepository<Locality, Long> {
}
