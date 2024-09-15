package calendly.comparator;

import calendly.model.Schedule;

import java.util.Comparator;

public class DateComparator implements Comparator<Schedule> {
    @Override
    public int compare(Schedule s1, Schedule s2) {
        if (s1.getDate() == null && s2.getDate() == null) {
            return 0;
        } else if (s1.getDate() == null) {
            return -1; // null dates are considered "less" than non-null dates
        } else if (s2.getDate() == null) {
            return 1;
        }
        return s1.getDate().compareTo(s2.getDate());
    }
}
