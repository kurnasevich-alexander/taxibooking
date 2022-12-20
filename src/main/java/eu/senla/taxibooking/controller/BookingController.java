package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.dto.BookingDto;
import eu.senla.taxibooking.dto.OnCreate;
import eu.senla.taxibooking.dto.OnUpdate;
import eu.senla.taxibooking.service.mapper.BookingDtoMapper;
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
    private final BookingDtoMapper bookingDTOMapper;

    @GetMapping
    public ResponseEntity<Page<BookingDto>> getBookings(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookingDTOMapper.pageBookingToPageBookingDto(bookingService.getAllBookings(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingDTOMapper.bookingToBookingDto(bookingService.getBooking(id)));
    }

    @PostMapping
    public ResponseEntity<BookingDto> addBooking(@Validated(OnCreate.class) @RequestBody BookingDto bookingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingDTOMapper.bookingToBookingDto(bookingService.addBooking(bookingDTOMapper
                        .bookingDtoToBooking(bookingDTO))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id,
                                                    @Validated(OnUpdate.class) @RequestBody BookingDto bookingDTO) {
        bookingDTO.setId(id);
        return ResponseEntity.ok(bookingDTOMapper.bookingToBookingDto(bookingService
                .updateBooking(bookingDTOMapper.bookingDtoToBooking(bookingDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

}
