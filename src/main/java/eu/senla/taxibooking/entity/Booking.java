package eu.senla.taxibooking.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    @Column(name = "passenger_name")
    private String passengerName;
    @EqualsAndHashCode.Include
    @Column(name = "passenger_contact_number")
    private Long passengerContactNumber;
    @EqualsAndHashCode.Include
    @Column(name = "pickup_time")
    private OffsetDateTime pickupTime;
    @EqualsAndHashCode.Include
    @Column(name = "asap")
    private Boolean asap;
    @EqualsAndHashCode.Include
    @Column(name = "waiting_time")
    private Integer waitingTime;
    @EqualsAndHashCode.Include
    @Column(name = "number_of_passengers")
    private Integer numberOfPassengers;
    @EqualsAndHashCode.Include
    @Column(name = "price")
    private BigDecimal price;
    @EqualsAndHashCode.Include
    @Column(name = "rating")
    private Float rating;
    @EqualsAndHashCode.Include
    @Column(name = "created_on")
    private OffsetDateTime createdOn;
    @EqualsAndHashCode.Include
    @Column(name = "last_modified_on")
    private OffsetDateTime lastModifiedOn;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waypoint> tripWaypoints;
}
