package eu.senla.taxibooking.api.service;

import eu.senla.taxibooking.dto.BookingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    BookingDTO addBooking(BookingDTO bookingDTO);

    BookingDTO updateBooking(BookingDTO bookingDTO);

    void deleteBooking(Long id);

    List<BookingDTO> getAllBookings(Pageable pageable);

    BookingDTO getBooking(Long id);
}
