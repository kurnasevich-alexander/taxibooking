package eu.senla.taxibooking.service;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.dto.mapper.BookingDTOMapper;
import eu.senla.taxibooking.entity.Booking;
import eu.senla.taxibooking.entity.Waypoint;
import eu.senla.taxibooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDTOMapper mapper;

    @Override
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        Booking booking = (mapper.bookingDTOToEntity(bookingDTO));
        booking.setCreatedOn(LocalDateTime.now());
        booking.setLastModifiedOn(LocalDateTime.now());
        for (Waypoint waypoint : booking.getTripWaypoints()) {
            waypoint.setBooking(booking);
        }
        return mapper.bookingToDTO(bookingRepository.save(booking));
    }

    @Override
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        Booking booking = (mapper.bookingDTOToEntity(bookingDTO));
        booking.setLastModifiedOn(LocalDateTime.now());
        return mapper.bookingToDTO(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = new Booking();
        booking.setId(id);
        bookingRepository.delete(booking);
    }

    @Override
    public Page<BookingDTO> getAllBookings(Pageable pageable) {
        return mapper.bookingPageToDTO(bookingRepository.findAll(pageable));
    }

    @Override
    public BookingDTO getBooking(Long id) {
        return mapper.bookingToDTO(bookingRepository.getReferenceById(id));
    }
}
