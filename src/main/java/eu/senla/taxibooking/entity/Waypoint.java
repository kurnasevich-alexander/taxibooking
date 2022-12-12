package eu.senla.taxibooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Waypoint {

    @Id
    private Long id;
    @ManyToOne
    private Locality locality;
    private Double latitude;
    private Double longitude;
}
