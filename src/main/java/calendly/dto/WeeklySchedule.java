package calendly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Weekly schedule with a specific weekday")
@NoArgsConstructor
public class WeeklySchedule extends Schedule {

    @Schema(description = "Day of the week", example = "Monday")
    private String weekDay;
}
