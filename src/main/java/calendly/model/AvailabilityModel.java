package calendly.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "availabilities")
@Data
public class AvailabilityModel {
    @Id
    private Long userId;
    private List<Schedule> schedules;
}

