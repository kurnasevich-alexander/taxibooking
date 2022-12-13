package eu.senla.taxibooking.api.service;

import eu.senla.taxibooking.dto.BookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingDTO addBooking(BookingDTO bookingDTO);

    BookingDTO updateBooking(BookingDTO bookingDTO);

    void deleteBooking(Long id);

    Page<BookingDTO> getAllBookings(Pageable pageable);

    BookingDTO getBooking(Long id);
}
