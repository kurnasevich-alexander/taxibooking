package eu.senla.taxibooking_producer.controller;

import eu.senla.taxibooking_model.dto.BookingDto;
import eu.senla.taxibooking_model.dto.OnCreate;
import eu.senla.taxibooking_model.dto.OnUpdate;
import eu.senla.taxibooking_producer.api.service.BookingProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produce/bookings")
@RequiredArgsConstructor
public class BookingProducerController implements BookingProducerControllerOpenAPI {

    @Autowired
    private BookingProducerService bookingProducerService;

    @PostMapping
    public ResponseEntity<Void> addBooking(@Validated(OnCreate.class) @RequestBody BookingDto bookingDto) {
        bookingProducerService.addBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBooking(@PathVariable Long id,
                                              @Validated(OnUpdate.class) @RequestBody BookingDto bookingDto) {
        bookingDto.setId(id);
        bookingProducerService.updateBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingProducerService.deleteBooking(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
