package eu.senla.taxibooking.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
    private OffsetDateTime pickupTime;
    @Column(name = "asap")
    private Boolean asap;
    @Column(name = "waiting_time")
    private Integer waitingTime;
    @Column(name = "number_of_passengers")
    private Integer numberOfPassengers;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "rating")
    private Float rating;
    @Column(name = "created_on")
    private OffsetDateTime createdOn;
    @Column(name = "last_modified_on")
    private OffsetDateTime lastModifiedOn;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waypoint> tripWaypoints;
}
