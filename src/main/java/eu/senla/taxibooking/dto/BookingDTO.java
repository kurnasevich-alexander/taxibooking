package eu.senla.taxibooking.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {

    private Long id;
    private String passengerName;
    private Long passengerContactNumber;
    private LocalDateTime pickupTime;
    private Boolean asap;
    private Integer waitingTime;
    private Integer numberOfPassengers;
    private Double price;
    private Float rating;
    private LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;
    private List<WaypointDTO> tripWaypoints;
}
