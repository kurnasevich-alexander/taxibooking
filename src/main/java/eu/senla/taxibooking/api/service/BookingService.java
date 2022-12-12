package eu.senla.taxibooking.api.service;

import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.entity.Booking;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    void addBooking(BookingDTO bookingDTO);
    void updateBooking(BookingDTO bookingDTO);
    void deleteBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAllBookings(Pageable pageable);
    Booking getBooking(Long id);
}
