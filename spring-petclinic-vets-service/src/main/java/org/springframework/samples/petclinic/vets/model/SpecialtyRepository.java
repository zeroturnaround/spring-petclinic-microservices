package org.springframework.samples.petclinic.vets.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    Optional<Specialty> findSpecialtyByName(String name);
}
