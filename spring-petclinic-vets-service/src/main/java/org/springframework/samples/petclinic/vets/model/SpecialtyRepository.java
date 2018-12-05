package org.springframework.samples.petclinic.vets.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    Optional<Specialty> findSpecialtyByName(String name);
}
