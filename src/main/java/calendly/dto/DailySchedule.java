package calendly.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Daily schedule with a date")
@NoArgsConstructor
public class DailySchedule extends Schedule {

    @Schema(description = "Date for the schedule", example = "2024-09-16")
    private LocalDate date;
}
