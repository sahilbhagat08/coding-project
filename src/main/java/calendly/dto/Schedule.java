package calendly.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "A schedule containing fromTime and toTime")
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DailySchedule.class, name = "daily"),
        @JsonSubTypes.Type(value = WeeklySchedule.class, name = "weekly")
})
public abstract class Schedule {

    @NonNull
    @Schema(description = "Start time of the schedule", example = "09")
    private int fromTime;

    @NonNull
    @Schema(description = "End time of the schedule", example = "10")
    private int toTime;
}





