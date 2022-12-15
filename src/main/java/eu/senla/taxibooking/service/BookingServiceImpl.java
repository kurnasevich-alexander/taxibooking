package eu.senla.taxibooking.service;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.dto.mapper.BookingDTOMapper;
import eu.senla.taxibooking.entity.Booking;
import eu.senla.taxibooking.entity.Waypoint;
import eu.senla.taxibooking.exception.BookingNotFoundException;
import eu.senla.taxibooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDTOMapper mapper;

    @Override
    @Transactional
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        bookingDTO.setId(0L);
        Booking booking = (mapper.bookingDTOToEntity(bookingDTO));
        booking.setCreatedOn(LocalDateTime.now());
        booking.setLastModifiedOn(LocalDateTime.now());
        insertBookingIntoWaypoints(booking);
        return mapper.bookingToDTO(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingDTO.getId())
                .orElseThrow(() -> new BookingNotFoundException("Booking not found id: " + bookingDTO.getId()));
        bookingDTO.setLastModifiedOn(LocalDateTime.now());
        bookingDTO.setCreatedOn(booking.getCreatedOn());
        mapper.updateBookingFromDTO(bookingDTO, booking);
        insertBookingIntoWaypoints(booking);
        return mapper.bookingToDTO(bookingRepository.save(booking));
    }

    private void insertBookingIntoWaypoints(Booking booking) {
        for (Waypoint waypoint : booking.getTripWaypoints()) {
            waypoint.setId(0L);
            waypoint.setBooking(booking);
        }
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else throw new BookingNotFoundException("Booking not found id: " + id);
    }

    @Override
    public Page<BookingDTO> getAllBookings(Pageable pageable) {
        return mapper.bookingPageToDTO(bookingRepository.findAll(pageable));
    }

    @Override
    public BookingDTO getBooking(Long id) {
        return bookingRepository.findById(id)
                .map(mapper::bookingToDTO)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found id: " + id));
    }
}
