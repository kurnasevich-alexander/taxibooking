package eu.senla.taxibooking.api.service;

import eu.senla.taxibooking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    Booking addBooking(Booking booking);

    Booking updateBooking(Booking booking);

    void deleteBooking(Long id);

    Page<Booking> getAllBookings(Pageable pageable);

    Booking getBooking(Long id);

}
