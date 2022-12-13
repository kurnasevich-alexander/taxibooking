package eu.senla.taxibooking.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "passenger_name")
    private String passengerName;
    @Column(name = "passenger_contact_number")
    private Long passengerContactNumber;
    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;
    @Column(name = "asap")
    private Boolean asap;
    @Column(name = "waiting_time")
    private Integer waitingTime;
    @Column(name = "number_of_passengers")
    private Integer numberOfPassengers;
    @Column(name = "price")
    private Double price;
    @Column(name = "rating")
    private Float rating;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "last_modified_on")
    private LocalDateTime lastModifiedOn;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waypoint> tripWaypoints;

}
