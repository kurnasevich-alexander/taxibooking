package eu.senla.taxibooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaypointDTO {
    @Schema(description = "Unique identifier of the Waypoint.",
            example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;
    @Schema(description = "Comment to the passenger location.",
            example = "Market parking", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String locality;
    @Schema(description = "Latitude of the waypoint.",
            example = "-41.9985", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double latitude;
    @Schema(description = "Longitude of the waypoint.",
            example = "-41.9985", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double longitude;
}
