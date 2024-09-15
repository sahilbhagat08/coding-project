package calendly.services;

import calendly.comparator.DateComparator;
import calendly.comparator.WeekDayComparator;
import calendly.dto.OverlapSlot;
import calendly.model.AvailabilityModel;
import calendly.model.Schedule;
import calendly.utils.AvailabilityUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OverlapServiceImpl implements IOverlapService {

    @Override
    public List<OverlapSlot> getOverlapSlots(AvailabilityModel firstUser, AvailabilityModel secondUser) {
        List<OverlapSlot> slots = new ArrayList<>(Collections.emptyList());

        // Approach 1 - Compute daily intersection
        List<Schedule> firstUserDailySchedule = AvailabilityUtils.getDailySchedules(firstUser);
        List<Schedule> secondUserDailySchedule = AvailabilityUtils.getDailySchedules(secondUser);
        Set<OverlapSlot> dailyIntersection = AvailabilityUtils.compareTwoSchedulesWithComparator(firstUserDailySchedule,
                secondUserDailySchedule,
                new DateComparator());
        slots.addAll(dailyIntersection);

        // Approach 2 - Compute weekly intersection
        List<Schedule> firstUserWeeklySchedule = AvailabilityUtils.getWeeklySchedules(firstUser);
        List<Schedule> secondUserWeeklySchedule = AvailabilityUtils.getWeeklySchedules(secondUser);
        Set<OverlapSlot> weeklyIntersection = AvailabilityUtils.compareTwoSchedulesWithComparator(firstUserWeeklySchedule,
                secondUserWeeklySchedule,
                new WeekDayComparator());
        slots.addAll(weeklyIntersection);
        return slots;
    }
}
