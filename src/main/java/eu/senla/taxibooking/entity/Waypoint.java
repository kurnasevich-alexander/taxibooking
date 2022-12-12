package eu.senla.taxibooking.entity;

import javax.persistence.*;
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
