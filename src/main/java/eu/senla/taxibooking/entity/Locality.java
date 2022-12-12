package eu.senla.taxibooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Locality {

    @Id
    private Long id;
    @Column(name = "locality_name")
    private String localityName;
}
