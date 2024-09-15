package calendly.services;

import calendly.comparator.DateComparator;
import calendly.comparator.WeekDayComparator;
import calendly.dto.OverlapSlot;
import calendly.model.AvailabilityModel;
import calendly.model.Schedule;
import calendly.utils.AvailabilityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AvailabilityUtils.class)  // Prepare for static method mocking
public class OverlapServiceImplTest {

    @InjectMocks
    private OverlapServiceImpl overlapService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(AvailabilityUtils.class);  // Mock static methods
    }

    @Test
    public void testGetOverlapSlots_DailyIntersection() {
        // Mock data for first and second user's daily schedules
        Schedule firstUserDailySchedule = new Schedule();
        firstUserDailySchedule.setFromTime(9);
        firstUserDailySchedule.setToTime(11);
        firstUserDailySchedule.setDate(LocalDate.of(2024, 9, 16));

        Schedule secondUserDailySchedule = new Schedule();
        secondUserDailySchedule.setFromTime(10);
        secondUserDailySchedule.setToTime(12);
        secondUserDailySchedule.setDate(LocalDate.of(2024, 9, 16));

        List<Schedule> firstUserDailySchedules = Arrays.asList(firstUserDailySchedule);
        List<Schedule> secondUserDailySchedules = Arrays.asList(secondUserDailySchedule);

        AvailabilityModel firstUser = mock(AvailabilityModel.class);
        AvailabilityModel secondUser = mock(AvailabilityModel.class);

        // Mock the utility partitioning function
        PowerMockito.when(AvailabilityUtils.getDailySchedules(firstUser)).thenReturn(firstUserDailySchedules);
        PowerMockito.when(AvailabilityUtils.getDailySchedules(secondUser)).thenReturn(secondUserDailySchedules);

        // Mock the comparison function
        Set<OverlapSlot> dailyIntersection = new HashSet<>(Arrays.asList(OverlapSlot.builder()
                .date("2024-09-16")
                .fromTime(10)
                .toTime(11)
                .build()));

        Comparator<Schedule> dailyComparator = new DateComparator();
        PowerMockito.when(AvailabilityUtils.compareTwoSchedulesWithComparator(anyList(), anyList(), any(DateComparator.class)))
                .thenReturn(dailyIntersection);

        // Call the method
        List<OverlapSlot> overlapSlots = overlapService.getOverlapSlots(firstUser, secondUser);

        // Verify the result
        assertEquals(1, overlapSlots.size());
        OverlapSlot slot = overlapSlots.get(0);
        assertEquals("2024-09-16", slot.getDate());
        assertEquals(10, slot.getFromTime());
        assertEquals(11, slot.getToTime());
    }

    @Test
    public void testGetOverlapSlots_WeeklyIntersection() {
        // Mock data for first and second user's weekly schedules
        Schedule firstUserWeeklySchedule = new Schedule();
        firstUserWeeklySchedule.setFromTime(9);
        firstUserWeeklySchedule.setToTime(11);
        firstUserWeeklySchedule.setWeekDay("Monday");

        Schedule secondUserWeeklySchedule = new Schedule();
        secondUserWeeklySchedule.setFromTime(10);
        secondUserWeeklySchedule.setToTime(12);
        secondUserWeeklySchedule.setWeekDay("Monday");

        List<Schedule> firstUserWeeklySchedules = Arrays.asList(firstUserWeeklySchedule);
        List<Schedule> secondUserWeeklySchedules = Arrays.asList(secondUserWeeklySchedule);

        AvailabilityModel firstUser = mock(AvailabilityModel.class);
        AvailabilityModel secondUser = mock(AvailabilityModel.class);

        PowerMockito.when(AvailabilityUtils.getWeeklySchedules(firstUser)).thenReturn(firstUserWeeklySchedules);
        PowerMockito.when(AvailabilityUtils.getWeeklySchedules(secondUser)).thenReturn(secondUserWeeklySchedules);

        // Mock the comparison function
        Set<OverlapSlot> weeklyIntersection = new HashSet<>(Arrays.asList(OverlapSlot.builder()
                .weekday("Monday")
                .fromTime(10)
                .toTime(11)
                .build()));

        PowerMockito.when(AvailabilityUtils.compareTwoSchedulesWithComparator(anyList(), anyList(), any(WeekDayComparator.class)))
                .thenReturn(weeklyIntersection);

        // Call the method
        List<OverlapSlot> overlapSlots = overlapService.getOverlapSlots(firstUser, secondUser);

        // Verify the result
        assertEquals(1, overlapSlots.size());
        OverlapSlot slot = overlapSlots.get(0);
        assertEquals("Monday", slot.getWeekday());
        assertEquals(10, slot.getFromTime());
        assertEquals(11, slot.getToTime());
    }

    @Test
    public void testGetOverlapSlots_NoOverlap() {
        // Mock empty schedules
        List<Schedule> firstUserSchedules = new ArrayList<>();
        List<Schedule> secondUserSchedules = new ArrayList<>();

        AvailabilityModel firstUser = mock(AvailabilityModel.class);
        AvailabilityModel secondUser = mock(AvailabilityModel.class);

        PowerMockito.when(AvailabilityUtils.getDailySchedules(firstUser)).thenReturn(firstUserSchedules);
        PowerMockito.when(AvailabilityUtils.getDailySchedules(secondUser)).thenReturn(secondUserSchedules);

        PowerMockito.when(AvailabilityUtils.compareTwoSchedulesWithComparator(anyList(), anyList(), any(DateComparator.class)))
                .thenReturn(new HashSet<>());

        PowerMockito.when(AvailabilityUtils.compareTwoSchedulesWithComparator(anyList(), anyList(), any(WeekDayComparator.class)))
                .thenReturn(new HashSet<>());

        // Call the method
        List<OverlapSlot> overlapSlots = overlapService.getOverlapSlots(firstUser, secondUser);

        // Verify the result
        assertEquals(0, overlapSlots.size());
    }
}
