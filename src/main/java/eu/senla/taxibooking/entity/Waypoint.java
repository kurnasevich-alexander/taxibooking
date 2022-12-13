package eu.senla.taxibooking.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "waypoints")
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locality;
    private Double latitude;
    private Double longitude;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
