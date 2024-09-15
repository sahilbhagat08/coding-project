package calendly.services;

import calendly.dto.OverlapSlot;
import calendly.model.AvailabilityModel;

import java.util.List;

public interface IOverlapService {

    List<OverlapSlot> getOverlapSlots(AvailabilityModel firstUser, AvailabilityModel secondUser);
}
