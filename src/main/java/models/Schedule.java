package models;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

public class Schedule {
    private final DayOfWeek dayOfWeek;
    private final Date hour;

    public Schedule(DayOfWeek dayOfWeek, String hour) {
        this.dayOfWeek = validatePossibleDay(dayOfWeek);
        this.hour = validatePossibleHour(hour);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Date getHour() {
        return hour;
    }

    private DayOfWeek validatePossibleDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            return dayOfWeek;
        }
        throw new IllegalArgumentException("You must enter a day between Monday and Friday");
    }

    private Date validatePossibleHour(String hour) {
        try {
            return new SimpleDateFormat("hh:mm").parse(hour);
        } catch (Exception e) {
            throw new IllegalArgumentException("You must enter a valid hour format");
        }
    }
}
