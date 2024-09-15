package calendly.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OverlapSlot {
    private String date;
    private int fromTime;
    private int toTime;
    private String weekday;
}
