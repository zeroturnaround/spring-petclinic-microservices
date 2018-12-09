/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vets.web;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.samples.petclinic.vets.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Szarlinski
 */
@RequestMapping("/vets")
@RestController
@RequiredArgsConstructor
@Slf4j
@Component
class VetResource {

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;
    private final RestTemplate loadBalancedRestTemplate;

    @GetMapping
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }

    /**
     * Read single Vet
     */
    @GetMapping(value = "/{vetId}")
    public Optional<Vet> findVet(@PathVariable("vetId") int vetId) {
        OwnerDetails[] ownerDetailsList = loadBalancedRestTemplate.getForObject("http://customers-service/owners/", OwnerDetails[].class);
        Optional<Vet> vet = vetRepository.findById(vetId);
        vet.get().setOwners(Arrays.asList(ownerDetailsList));
        return vet;
    }

    /**
     * Update Vet
     */
    @PutMapping(value = "/{vetId}")
    public Vet updateOwner(@PathVariable("vetId") int vetId, @Valid @RequestBody Vet vetRequest) {
        final Optional<Vet> vet = findVet(vetId);

        final Vet vetModel = vet.get();
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        vetModel.setFirstName(vetRequest.getFirstName());
        vetModel.setLastName(vetRequest.getLastName());
        List<Specialty> specialties = new ArrayList<>();

        for (Specialty specialty : vetRequest.getSpecialties()) {

            if (!specialty.getName().isEmpty()) {
                List<Specialty> allSpecialties = specialtyRepository.findAll();
                boolean foundSpeciality = false;
                for (Specialty existingSpeciality : allSpecialties) {
                    if (existingSpeciality.getName().equals(specialty.getName())) {
                        foundSpeciality = true;
                        specialties.add(existingSpeciality);
                    }
                }
                if (!foundSpeciality) {
                    Specialty savedSpeciality = specialtyRepository.save(specialty);
                    specialties.add(savedSpeciality);
                }
            }
        }
        vetModel.setSpecialties(specialties);
        return vetRepository.save(vetModel);
    }
}
