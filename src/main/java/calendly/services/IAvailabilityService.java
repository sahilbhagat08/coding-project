package calendly.services;

import calendly.dto.*;

public interface IAvailabilityService {

    AvailabilityResponse createAvailability(CreateAvailabilityRequest request);
    AvailabilityResponse getUserAvailability(AvailabilityRequest request);
    OverlapResponse getOverlap(OverlapRequest request);
}

