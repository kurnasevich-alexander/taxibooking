package eu.senla.taxibooking.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;
        return Objects.equals(id, booking.id)
                && Objects.equals(passengerName, booking.passengerName)
                && Objects.equals(passengerContactNumber, booking.passengerContactNumber)
                && Objects.equals(pickupTime, booking.pickupTime)
                && Objects.equals(asap, booking.asap)
                && Objects.equals(waitingTime, booking.waitingTime)
                && Objects.equals(numberOfPassengers, booking.numberOfPassengers)
                && Objects.equals(price, booking.price)
                && Objects.equals(rating, booking.rating)
                && Objects.equals(createdOn, booking.createdOn)
                && Objects.equals(lastModifiedOn, booking.lastModifiedOn)
                && Objects.equals(tripWaypoints, booking.tripWaypoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passengerName, passengerContactNumber, pickupTime, asap, waitingTime,
                numberOfPassengers, price, rating, createdOn, lastModifiedOn);
    }
}
