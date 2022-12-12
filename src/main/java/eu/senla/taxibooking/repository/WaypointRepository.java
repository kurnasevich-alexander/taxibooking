package eu.senla.taxibooking.repository;

import eu.senla.taxibooking.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaypointRepository extends JpaRepository<Waypoint, Long> {
}
