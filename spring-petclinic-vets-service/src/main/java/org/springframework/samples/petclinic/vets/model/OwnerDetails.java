package org.springframework.samples.petclinic.vets.model;
import lombok.Data;

/**
 * @author Maciej Szarlinski
 */
@Data
public class OwnerDetails {

    private int id;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String telephone;

}
