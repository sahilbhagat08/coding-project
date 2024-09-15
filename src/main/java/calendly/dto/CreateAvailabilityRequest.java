package calendly.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Request to create availability for a user")
public class CreateAvailabilityRequest {

    @NonNull
    @Schema(description = "The ID of the user", example = "1")
    private Long userId;

    @Schema(description = "Daily schedule list")
    private List<Schedule> schedules;
}

