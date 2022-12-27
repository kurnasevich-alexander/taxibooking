package eu.senla.taxibooking.service;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import eu.senla.taxibooking.repository.BookingRepository;
import eu.senla.taxibooking.service.mapper.BookingUpdateMapper;
import eu.senla.taxibooking.entity.Booking;
import eu.senla.taxibooking.entity.Waypoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private static final String BOOKING_NOT_FOUND_MESSAGE = "Booking not found id: ";

    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private final BookingUpdateMapper mapper;

    @Override
    @Transactional
    public Booking addBooking(Booking booking) {
        booking.setId(null);
        booking.setCreatedOn(OffsetDateTime.now());
        booking.setLastModifiedOn(OffsetDateTime.now());
        insertBookingIntoWaypoints(booking);
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        Booking updated = bookingRepository.findById(booking.getId())
                .orElseThrow(() -> new EntityNotFoundException(BOOKING_NOT_FOUND_MESSAGE + booking.getId()));
        mapper.updateBookingFromBooking(booking, updated);
        if (booking.getTripWaypoints() != null && !booking.getTripWaypoints().isEmpty()) {
            insertBookingIntoWaypoints(updated);
        }
        updated.setLastModifiedOn(OffsetDateTime.now());
        return bookingRepository.save(updated);
    }

    private void insertBookingIntoWaypoints(Booking booking) {
        for (Waypoint waypoint : booking.getTripWaypoints()) {
            waypoint.setId(null);
            waypoint.setBooking(booking);
        }
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(BOOKING_NOT_FOUND_MESSAGE + id);
        }
    }

    @Override
    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(BOOKING_NOT_FOUND_MESSAGE + id));
    }
}
