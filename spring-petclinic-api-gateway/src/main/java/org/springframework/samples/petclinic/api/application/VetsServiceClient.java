package org.springframework.samples.petclinic.api.application;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.api.dto.OwnerDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class VetsServiceClient {

    private final RestTemplate loadBalancedRestTemplate;

    public OwnerDetails getOwner(final int ownerId) {
        return loadBalancedRestTemplate.getForObject("http://customers-service/owners/{ownerId}", OwnerDetails.class, ownerId);
    }

}
