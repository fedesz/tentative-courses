package models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Schedule {
    private final DayOfWeek dayOfWeek;
    private final LocalTime hour;

    public Schedule(DayOfWeek dayOfWeek, String hour) {
        this.dayOfWeek = validatePossibleDay(dayOfWeek);
        this.hour = validatePossibleHour(hour);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return dayOfWeek == schedule.dayOfWeek &&
                Objects.equals(hour, schedule.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, hour);
    }

    private DayOfWeek validatePossibleDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            return dayOfWeek;
        }
        throw new IllegalArgumentException("You must enter a day between Monday and Friday");
    }

    private LocalTime validatePossibleHour(String hour) {
        try {
            return LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            throw new IllegalArgumentException("You must enter a valid hour format");
        }
    }
}
