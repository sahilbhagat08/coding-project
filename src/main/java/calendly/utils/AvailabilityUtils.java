package calendly.utils;

import calendly.dto.OverlapSlot;
import calendly.model.AvailabilityModel;
import calendly.model.Schedule;

import java.util.*;
import java.util.stream.Collectors;

public class AvailabilityUtils {

    public static List<Schedule> getDailySchedules(AvailabilityModel response) {
        List<Schedule> partitionedSchedules = response.getSchedules().stream()
                .filter(schedule -> schedule.getDate() != null)
                .collect(Collectors.toList());
        return partitionedSchedules;
    }

    public static List<Schedule> getWeeklySchedules(AvailabilityModel response) {
        List<Schedule> partitionedSchedules = response.getSchedules().stream()
                .filter(schedule -> schedule.getWeekDay() != null)
                .collect(Collectors.toList());
        return partitionedSchedules;
    }

    public static Set<OverlapSlot> compareTwoSchedulesWithComparator(List<Schedule> firstSchedule,
                                                                     List<Schedule> secondSchedule,
                                                                     Comparator<Schedule> comparator) {
        Set<OverlapSlot> slots = new HashSet<>();
        if(firstSchedule.size() ==0 || secondSchedule.size() ==0) {
            return slots;
        }
        Collections.sort(firstSchedule,comparator);
        Collections.sort(secondSchedule,comparator);

        int i=0,j =0;
        while(i < firstSchedule.size() || j < secondSchedule.size()) {
            Schedule first = firstSchedule.get(i);
            Schedule second = secondSchedule.get(i);
            int comparisonResult = comparator.compare(first, second);

            // Display the result
            if (comparisonResult < 0) {
                i++;
            } else if (comparisonResult > 0) {
                j++;
            } else {
                Optional<Schedule> intersection = first.intersection(second);
                if (intersection.isPresent()) {
                    OverlapSlot.OverlapSlotBuilder builder = OverlapSlot.builder()
                            .fromTime(intersection.get().getFromTime())
                            .toTime(intersection.get().getToTime());
                    if(intersection.get().getDate()!=null) builder.date(intersection.get().getDate().toString());
                    if(intersection.get().getWeekDay()!=null) builder.weekday(intersection.get().getWeekDay());

                    OverlapSlot slot = builder.build();
                    slots.add(slot);
                }
                i++;
                j++;
            }

        }
        return slots;
    }
}
