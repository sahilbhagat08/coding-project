package calendly.utils;

import calendly.comparator.DateComparator;
import calendly.comparator.WeekDayComparator;
import calendly.dto.OverlapSlot;
import calendly.model.AvailabilityModel;
import calendly.model.Schedule;
import calendly.utils.AvailabilityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityUtilsTest {

    private AvailabilityModel availabilityModel;

    @BeforeEach
    public void setUp() {
        availabilityModel = mock(AvailabilityModel.class);
    }

    @Test
    public void testCompareTwoSchedulesWithDateComparator() {
        // Prepare test data
        Schedule firstSchedule1 = new Schedule();
        firstSchedule1.setFromTime(9);
        firstSchedule1.setToTime(11);
        firstSchedule1.setDate(LocalDate.of(2024, 9, 16));

        Schedule firstSchedule2 = new Schedule();
        firstSchedule2.setFromTime(13);
        firstSchedule2.setToTime(15);
        firstSchedule2.setDate(LocalDate.of(2024, 9, 16));

        Schedule secondSchedule1 = new Schedule();
        secondSchedule1.setFromTime(10);
        secondSchedule1.setToTime(12);
        secondSchedule1.setDate(LocalDate.of(2024, 9, 16));

        Schedule secondSchedule2 = new Schedule();
        secondSchedule2.setFromTime(14);
        secondSchedule2.setToTime(16);
        secondSchedule2.setDate(LocalDate.of(2024, 9, 16));

        List<Schedule> firstScheduleList = Arrays.asList(firstSchedule1, firstSchedule2);
        List<Schedule> secondScheduleList = Arrays.asList(secondSchedule1, secondSchedule2);

        Comparator<Schedule> comparator = new DateComparator();

        // Call the method
        Set<OverlapSlot> overlapSlots = AvailabilityUtils.compareTwoSchedulesWithComparator(firstScheduleList, secondScheduleList, comparator);

        // Verify the results
        assertEquals(2, overlapSlots.size(), "Should find two overlap slots");
    }

    @Test
    public void testCompareTwoSchedulesWithWeekdayComparator() {
        // Prepare test data
        Schedule firstSchedule1 = new Schedule();
        firstSchedule1.setFromTime(9);
        firstSchedule1.setToTime(11);
        firstSchedule1.setWeekDay("Monday");

        Schedule firstSchedule2 = new Schedule();
        firstSchedule2.setFromTime(13);
        firstSchedule2.setToTime(15);
        firstSchedule2.setWeekDay("Wednesday");

        Schedule secondSchedule1 = new Schedule();
        secondSchedule1.setFromTime(10);
        secondSchedule1.setToTime(12);
        secondSchedule1.setWeekDay("Monday");

        Schedule secondSchedule2 = new Schedule();
        secondSchedule2.setFromTime(14);
        secondSchedule2.setToTime(16);
        secondSchedule2.setWeekDay("Wednesday");

        List<Schedule> firstScheduleList = Arrays.asList(firstSchedule1, firstSchedule2);
        List<Schedule> secondScheduleList = Arrays.asList(secondSchedule1, secondSchedule2);

        Comparator<Schedule> comparator = new WeekDayComparator();

        // Call the method
        Set<OverlapSlot> overlapSlots = AvailabilityUtils.compareTwoSchedulesWithComparator(firstScheduleList, secondScheduleList, comparator);

        // Verify the results
        assertEquals(2, overlapSlots.size(), "Should find two overlap slots");
    }
}
