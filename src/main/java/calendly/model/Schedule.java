package calendly.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private int fromTime;
    private int toTime;
    private LocalDate date;
    private String weekDay;

    public Optional<Schedule> intersection(Schedule other) {
        if (this.date == other.date || this.weekDay == other.weekDay) {
            int start = Math.max(this.fromTime, other.fromTime);
            int end = Math.min(this.toTime, other.toTime);
            if (start < end) {
                Schedule intersection = new Schedule();
                if (this.date!=null) intersection.setDate(this.date);
                if (this.weekDay!=null) intersection.setWeekDay(this.weekDay);
                intersection.setFromTime(start);
                intersection.setToTime(end);
                return Optional.of(intersection);
            }
        }
        return Optional.empty();
    }
}
