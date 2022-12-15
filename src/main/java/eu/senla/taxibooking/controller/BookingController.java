package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.dto.BookingDTO;
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

    @GetMapping
    public ResponseEntity<Page<BookingDTO>> getBookings(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> addBooking(@Validated @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.addBooking(bookingDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        bookingDTO.setId(id);
        return ResponseEntity.ok(bookingService.updateBooking(bookingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

}
