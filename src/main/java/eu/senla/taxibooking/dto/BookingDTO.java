package eu.senla.taxibooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {

    @Schema(description = "Unique identifier of the Booking.",
            example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;
    @Schema(description = "Passenger name.",
            example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    private String passengerName;
    @Schema(description = "Unique identifier of the Contact.",
            example = "48123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive
    private Long passengerContactNumber;
    @Schema(description = "Time when passenger was picked up.",
            example = "2000-11-29T19:03:16.900Z", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime pickupTime;
    @Schema(description = "Is taxi required as soon as possible boolean flag.",
            example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean asap;
    @Schema(description = "Time waited by passenger.",
            example = "69", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive
    private Integer waitingTime;
    @Schema(description = "Number of passengers in taxi.",
            example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive
    private Integer numberOfPassengers;
    @Schema(description = "Price of the ride.",
            example = "16.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive
    private Double price;
    @Schema(description = "Rating of the ride.",
            example = "4.7", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive
    private Float rating;
    @Schema(description = "Time when booking was created.",
            example = "2000-11-29T19:03:16.900Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime createdOn;
    @Schema(description = "Time of last update of the booking.",
            example = "2000-11-29T19:03:16.900Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime lastModifiedOn;
    @Schema(description = "Collection of trip waypoints.", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<WaypointDTO> tripWaypoints;
}
