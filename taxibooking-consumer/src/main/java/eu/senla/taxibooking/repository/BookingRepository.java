package eu.senla.taxibooking.repository;

import eu.senla.taxibooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
