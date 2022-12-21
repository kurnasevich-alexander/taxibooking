package eu.senla.taxibooking.service;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.entity.Booking;
import eu.senla.taxibooking.entity.Waypoint;
import eu.senla.taxibooking.exception.EntityNotFoundException;
import eu.senla.taxibooking.repository.BookingRepository;
import eu.senla.taxibooking.service.mapper.BookingUpdateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private BookingUpdateMapper bookingUpdateMapper;
    private Clock clock;

    @BeforeEach
    public void init() {
        bookingRepository = Mockito.mock(BookingRepository.class);
        bookingUpdateMapper = Mockito.mock(BookingUpdateMapper.class);
        clock = Clock.fixed(Instant.ofEpochSecond(100000), ZoneId.systemDefault());
        bookingService = new BookingServiceImpl(bookingRepository, bookingUpdateMapper, clock);
    }

    @Test
    void addBooking() {
        List<Waypoint> givenWaypoints = new ArrayList<>();
        List<Waypoint> expectedWaypoints = new ArrayList<>();
        Booking given = Booking.builder()
                .tripWaypoints(givenWaypoints)
                .build();
        Booking expectedToReturn = Booking.builder()
                .id(1L)
                .createdOn(OffsetDateTime.now(clock))
                .lastModifiedOn(OffsetDateTime.now(clock))
                .tripWaypoints(expectedWaypoints)
                .build();
        Booking expectedToSave = Booking.builder()
                .createdOn(OffsetDateTime.now(clock))
                .lastModifiedOn(OffsetDateTime.now(clock))
                .tripWaypoints(givenWaypoints)
                .build();
        givenWaypoints.add(new Waypoint(null, "local", 9.9, 9.9, null));
        expectedWaypoints.add(new Waypoint(1L, "local", 9.9, 9.9, expectedToReturn));

        when(bookingRepository.save(given)).thenReturn(expectedToReturn);

        assertEquals(expectedToReturn, bookingService.addBooking(given));
        assertEquals(expectedToSave, given);
    }

    @Test
    void updateBooking() {
        List<Waypoint> givenWaypoints = new ArrayList<>();
        List<Waypoint> expectedWaypoints = new ArrayList<>();
        Booking given = Booking.builder().id(1L).tripWaypoints(expectedWaypoints).build();
        Booking expectedToReturn = Booking.builder().id(1L).tripWaypoints(givenWaypoints).build();
        Booking expectedToSave = Booking.builder().id(1L).tripWaypoints(givenWaypoints)
                .lastModifiedOn(OffsetDateTime.now(clock)).build();
        givenWaypoints.add(new Waypoint(null, "local", 9.9, 9.9, null));
        expectedWaypoints.add(new Waypoint(null, "local", 9.9, 9.9, null));

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(expectedToReturn));
        doNothing().when(bookingUpdateMapper).updateBookingFromBooking(given, expectedToReturn);
        when(bookingRepository.save(expectedToReturn)).thenReturn(expectedToReturn);

        assertEquals(expectedToReturn, bookingService.updateBooking(given));
        assertEquals(expectedToSave, expectedToReturn);
    }

    @Test
    void updateBooking_ThrowEntityNotFoundException() {
        when(bookingRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception actual = assertThrows(EntityNotFoundException.class, () -> {
            bookingService.getBooking(1L);
        });

        assertEquals("Booking not found id: 1", actual.getMessage());
    }

    @Test
    void deleteBooking() {
        when(bookingRepository.existsById(any(Long.class))).thenReturn(true);

        bookingService.deleteBooking(1L);

        verify(bookingRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void deleteBooking_ThrowEntityNotFoundException() {
        when(bookingRepository.existsById(any(Long.class))).thenReturn(false);

        Exception actual = assertThrows(EntityNotFoundException.class, () -> {
            bookingService.deleteBooking(1L);
        });

        assertEquals("Booking not found id: 1", actual.getMessage());
    }

    @Test
    void getAllBookings() {
        Page<Booking> expected = new PageImpl<>(List.of(new Booking()));

        when(bookingRepository.findAll(any(Pageable.class))).thenReturn(expected);

        assertEquals(expected, bookingService.getAllBookings(PageRequest.of(1, 1)));
    }

    @Test
    void getBooking() {
        Booking expected = new Booking();
        when(bookingRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        assertEquals(expected, bookingService.getBooking(1L));
    }

    @Test
    void getBooking_ThrowEntityNotFoundException() {
        when(bookingRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception actual = assertThrows(EntityNotFoundException.class, () -> {
            bookingService.getBooking(1L);
        });

        assertEquals("Booking not found id: 1", actual.getMessage());
    }
}