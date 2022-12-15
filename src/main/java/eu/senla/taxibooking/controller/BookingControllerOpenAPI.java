package eu.senla.taxibooking.controller;

import eu.senla.taxibooking.dto.BookingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;



@Tag(name = "booking", description = "the Booking API")
public interface BookingControllerOpenAPI {

    @Operation(summary = "Find all bookings", description = "Returns a Page of bookings", tags = {"booking"})
    @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = BookingDTO.class)))
    ResponseEntity<Page<BookingDTO>> getBookings(Pageable pageable);

    @Operation(summary = "Find booking by ID", description = "Returns a single booking", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content)})
    ResponseEntity<BookingDTO> getBooking(
            @Parameter(description = "Id of the booking to be obtained. Cannot be empty.", required = true) Long id);

    @Operation(summary = "Add a new booking", description = "Returns created booking", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created",
                    content = @Content(schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})

    ResponseEntity<BookingDTO> addBooking(
            @Parameter(description = "Booking to add. Cannot be null or empty.",
                    required = true, schema = @Schema(implementation = BookingDTO.class)) BookingDTO bookingDTO);

    @Operation(summary = "Update existing booking", description = "Returns updated booking", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking has been updated"),
            @ApiResponse(responseCode = "400", description = "Validation exception",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content)})

    ResponseEntity<BookingDTO> updateBooking(
            @Parameter(description = "Id of the booking to be updated. Cannot be empty.", required = true) Long id,
            @Parameter(description = "Booking to update. Cannot be null or empty.",
                    required = true, schema = @Schema(implementation = BookingDTO.class)) BookingDTO bookingDTO);

    @Operation(summary = "Deletes a booking", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking has been deleted"),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content)})

    ResponseEntity<Void> deleteBooking(
            @Parameter(description = "Id of the booking to be delete. Cannot be empty.",
                    required = true) Long id);
}
