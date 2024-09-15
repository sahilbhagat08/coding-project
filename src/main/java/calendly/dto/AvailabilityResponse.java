package calendly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class AvailabilityResponse {
    private Long userId;
    private List<? extends Schedule> dailySchedule;
    private List<? extends Schedule> weeklySchedule;
}

