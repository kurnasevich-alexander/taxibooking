package eu.senla.taxibooking.dto;

import lombok.Data;

@Data
public class WaypointDTO {

    private Long id;
    private String locality;
    private Double latitude;
    private Double longitude;
}
