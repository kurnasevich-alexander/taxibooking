package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.dto.BookingDTO;
import eu.senla.taxibooking.service.mapper.BookingDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController implements BookingControllerOpenAPI {

    @Autowired
    private final BookingService bookingService;

    @Autowired
    private final BookingDTOMapper bookingDTOMapper;

    @GetMapping
    public ResponseEntity<Page<BookingDTO>> getBookings(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookingDTOMapper.pageBookingToPageBookingDTO(bookingService.getAllBookings(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingDTOMapper.bookingToBookingDTO(bookingService.getBooking(id)));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> addBooking(@Validated @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingDTOMapper.bookingToBookingDTO(bookingService.addBooking(bookingDTOMapper
                        .bookingDTOToBooking(bookingDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id,
                                                    @Validated @RequestBody BookingDTO bookingDTO) {
        bookingDTO.setId(id);
        return ResponseEntity.ok(bookingDTOMapper.bookingToBookingDTO(bookingService
                .updateBooking(bookingDTOMapper.bookingDTOToBooking(bookingDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

}
