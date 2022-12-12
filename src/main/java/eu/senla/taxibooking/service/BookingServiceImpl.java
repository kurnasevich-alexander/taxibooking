package eu.senla.taxibooking.service;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.api.service.UtilService;
import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.entity.Booking;
import eu.senla.taxibooking.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UtilService utilService;

    @Override
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        Booking booking = (mapper.map(bookingDTO, Booking.class));
        booking.setCreatedOn(LocalDateTime.now());
        booking.setLastModifiedOn(LocalDateTime.now());
        return mapper.map(bookingRepository.save(booking), BookingDTO.class);
    }

    @Override
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        Booking booking = (mapper.map(bookingDTO, Booking.class));
        booking.setLastModifiedOn(LocalDateTime.now());
        return mapper.map(bookingRepository.save(booking), BookingDTO.class);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = new Booking();
        booking.setId(id);
        bookingRepository.delete(booking);
    }

    @Override
    public List<BookingDTO> getAllBookings(Pageable pageable) {
        return utilService.mapPage(bookingRepository.findAll(pageable), BookingDTO.class);
    }

    @Override
    public BookingDTO getBooking(Long id) {
        return mapper.map(bookingRepository.findById(id), BookingDTO.class);
    }
}
