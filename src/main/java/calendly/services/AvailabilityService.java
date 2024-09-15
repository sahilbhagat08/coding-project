package calendly.services;

import calendly.utils.MapperUtils;
import calendly.dto.*;
import calendly.model.AvailabilityModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import calendly.repositories.IAvailabilityRepository;

import java.util.*;

@Service
public class AvailabilityService implements IAvailabilityService {

    private final IAvailabilityRepository availabilityRepository;

    private final IOverlapService overlapService;

    private final MapperUtils mapperUtils;

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    @Autowired
    public AvailabilityService(IAvailabilityRepository availabilityRepository,
                               IOverlapService overlapService,
                               MapperUtils mapperUtils) {
        this.availabilityRepository = availabilityRepository;
        this.overlapService = overlapService;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public AvailabilityResponse createAvailability(CreateAvailabilityRequest request) {
        AvailabilityModel model = mapperUtils.convertCreateAvailabilityRequestToAvailabilityModel(request);
        AvailabilityModel response = availabilityRepository.save(model);
        logger.info("Response from db" + response);
        return mapperUtils.mapAvailabilityResponseModelToDTO(response);
    }

    @Override
    public AvailabilityResponse getUserAvailability(AvailabilityRequest request) {
        Optional<AvailabilityModel> availabilityModel = findAvailabilityById(request.getUserId());
        if(availabilityModel.isPresent()) {
            return mapperUtils.mapAvailabilityResponseModelToDTO(availabilityModel.get());
        }
        return null;
    }

    @Override
    public OverlapResponse getOverlap(OverlapRequest request) {
        Optional<AvailabilityModel> firstUser = findAvailabilityById(request.getFirstUserId());
        Optional<AvailabilityModel> secondUser = findAvailabilityById(request.getSecondUserId());

        if (!firstUser.isPresent() || !secondUser.isPresent()) {
            return OverlapResponse
                    .builder()
                    .overlapSlots(Collections.EMPTY_LIST)
                    .build();
        }
        List<OverlapSlot> slots = this.overlapService.getOverlapSlots(firstUser.get(),secondUser.get());
        return OverlapResponse
                .builder()
                .overlapSlots(slots)
                .build();
    }

    private Optional<AvailabilityModel> findAvailabilityById(Long userId) {
        Optional<AvailabilityModel> availabilityModel = availabilityRepository.findById(userId);
        return availabilityModel;
    }

}

