package eu.senla.taxibooking_model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto implements Serializable {
    @Schema(description = "Unique identifier of the Booking.",
            example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;
    @Schema(description = "Passenger name.",
            example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(groups = OnCreate.class)
    @Size(min = 1, max = 100, groups = {OnCreate.class, OnUpdate.class})
    private String passengerName;
    @Schema(description = "Unique identifier of the Contact.",
            example = "48123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Long passengerContactNumber;
    @Schema(description = "Time when passenger was picked up.",
            example = "2000-11-19T14:23:07.32478+01:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    private OffsetDateTime pickupTime;
    @Schema(description = "Is taxi required as soon as possible boolean flag.",
            example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    private Boolean asap;
    @Schema(description = "Time waited by passenger.",
            example = "69", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Integer waitingTime;
    @Schema(description = "Number of passengers in taxi.",
            example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Integer numberOfPassengers;
    @Schema(description = "Price of the ride.",
            example = "16.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private BigDecimal price;
    @Schema(description = "Rating of the ride.",
            example = "4.7", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(groups = OnCreate.class)
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Float rating;
    @Schema(description = "Time when booking was created.",
            example = "2000-11-19T14:23:07.32478+01:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private OffsetDateTime createdOn;
    @Schema(description = "Time of last update of the booking.",
            example = "2000-11-19T14:23:07.32478+01:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private OffsetDateTime lastModifiedOn;
    @Schema(description = "Collection of trip waypoints.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(groups = OnCreate.class)
    @Size(min = 1, groups = OnUpdate.class)
    private List<WaypointDto> tripWaypoints;
}
