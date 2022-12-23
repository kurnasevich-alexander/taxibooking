package eu.senla.taxibooking.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "waypoints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    private String locality;
    @EqualsAndHashCode.Include
    private Double latitude;
    @EqualsAndHashCode.Include
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
