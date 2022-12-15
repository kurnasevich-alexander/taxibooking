package eu.senla.taxibooking.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Locality {

    @Id
    private Long id;
    @Column(name = "locality_name")
    private String localityName;
}
