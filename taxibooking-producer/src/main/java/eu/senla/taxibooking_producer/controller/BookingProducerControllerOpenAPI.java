package eu.senla.taxibooking_producer.controller;

import eu.senla.taxibooking_model.dto.BookingDto;
import eu.senla.taxibooking_model.exception.ApiErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@Tag(name = "producer", description = "the Booking Producer API")
public interface BookingProducerControllerOpenAPI {

    @Operation(summary = "Add a new booking", description = "Posts booking to adding queue", tags = {"producer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Request has been accepted for processing"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))})
    ResponseEntity<Void> addBooking(
            @Parameter(description = "Booking to add. Cannot be null or empty.",
                    required = true, schema = @Schema(implementation = BookingDto.class)) BookingDto bookingDto);

    @Operation(summary = "Update existing booking", description = "Puts booking to editing queue", tags = {"producer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Request has been accepted for processing"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiErrorDto.class)))})
    ResponseEntity<Void> updateBooking(
            @Parameter(description = "Id of the booking to be updated. Cannot be empty.", required = true) Long id,
            @Parameter(description = "Booking to update. Cannot be null or empty.",
                    required = true, schema = @Schema(implementation = BookingDto.class)) @Valid BookingDto bookingDto);

    @Operation(summary = "Deletes a booking", description = "Puts booking to deleting queue", tags = {"producer"})
    @ApiResponse(responseCode = "202", description = "Request has been accepted for processing")
    ResponseEntity<Void> deleteBooking(@Parameter(description = "Id of the booking to be delete. Cannot be empty.",
                                        required = true) Long id);
}
