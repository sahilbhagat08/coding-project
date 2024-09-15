package calendly.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OverlapResponse {
    private List<OverlapSlot> overlapSlots;
}



