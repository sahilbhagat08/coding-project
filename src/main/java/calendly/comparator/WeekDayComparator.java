package calendly.comparator;

import calendly.model.Schedule;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WeekDayComparator implements Comparator<Schedule> {

    private static final Map<String, Integer> WEEKDAY_ORDER = new HashMap<>();

    static {
        WEEKDAY_ORDER.put("Monday", 1);
        WEEKDAY_ORDER.put("Tuesday", 2);
        WEEKDAY_ORDER.put("Wednesday", 3);
        WEEKDAY_ORDER.put("Thursday", 4);
        WEEKDAY_ORDER.put("Friday", 5);
        WEEKDAY_ORDER.put("Saturday", 6);
        WEEKDAY_ORDER.put("Sunday", 7);
    }

    @Override
    public int compare(Schedule s1, Schedule s2) {
        if (s1.getWeekDay() == null && s2.getWeekDay() == null) {
            return 0;
        } else if (s1.getWeekDay() == null) {
            return -1;
        } else if (s2.getWeekDay() == null) {
            return 1;
        }

        Integer order1 = WEEKDAY_ORDER.get(s1.getWeekDay());
        Integer order2 = WEEKDAY_ORDER.get(s2.getWeekDay());

        return order1.compareTo(order2);
    }
}

