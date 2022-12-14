package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.api.service.BookingService;
import eu.senla.taxibooking.dto.BookingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookings")
@Tag(name = "booking", description = "the Booking API")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Find all bookings", description = "Returns a Page of bookings", tags = { "booking" })
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookingDTO.class)))
    @GetMapping
    public ResponseEntity<Page<BookingDTO>> getBookings(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }

    @Operation(summary = "Find booking by ID", description = "Returns a single booking", tags = { "booking" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found") })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(
            @Parameter(description="Id of the booking to be obtained. Cannot be empty.", required=true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @Operation(summary = "Add a new booking", description = "Returns created booking", tags = { "booking" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created",
                    content = @Content(schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @PostMapping
    public ResponseEntity<BookingDTO> addBooking(
            @Parameter(description="Booking to add. Cannot be null or empty.",
            required=true, schema=@Schema(implementation = BookingDTO.class))
            @Valid @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.addBooking(bookingDTO));
    }

    @Operation(summary = "Update existing booking", description = "Returns updated booking", tags = { "booking" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking has been updated"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @Parameter(description="Id of the booking to be obtained. Cannot be empty.", required=true)
            @PathVariable Long id,
            @Parameter(description="Booking to update. Cannot be null or empty.",
                    required=true, schema=@Schema(implementation = BookingDTO.class))
            @Valid @RequestBody BookingDTO bookingDTO) {
        bookingDTO.setId(id);
        return ResponseEntity.ok(bookingService.updateBooking(bookingDTO));
    }

    @Operation(summary = "Deletes a booking", description = "", tags = { "booking" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking has been deleted"),
            @ApiResponse(responseCode = "404", description = "Booking not found") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description="Id of the booking to be delete. Cannot be empty.",
            required=true)
            @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
}
