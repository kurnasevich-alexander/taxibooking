package eu.senla.taxibooking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "waypoints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locality;
    private Double latitude;
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Waypoint waypoint)) return false;
        return Objects.equals(id, waypoint.id)
                && Objects.equals(locality, waypoint.locality)
                && Objects.equals(latitude, waypoint.latitude)
                && Objects.equals(longitude, waypoint.longitude)
                && Objects.equals(booking.getId(), waypoint.booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locality, latitude, longitude, booking);
    }
}
