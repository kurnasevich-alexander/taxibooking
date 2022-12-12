package eu.senla.taxibooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Waypoint {

    @Id
    private Long id;
    private String locality;
    private Double latitude;
    private Double longitude;
}
