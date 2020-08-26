package models;

import exceptions.OutOfRangeHourException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Schedule {
    private final DayOfWeek weekDay;
    private final LocalTime hour;

    public Schedule(DayOfWeek weekDay, String hour) {
        this.weekDay = validatePossibleDay(weekDay);
        this.hour = validatePossibleHour(hour);
    }

    public DayOfWeek getWeekDay() {
        return weekDay;
    }

    public LocalTime getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return weekDay == schedule.weekDay &&
                Objects.equals(hour, schedule.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekDay, hour);
    }

    private DayOfWeek validatePossibleDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            return dayOfWeek;
        }
        throw new IllegalArgumentException("You must enter a day between Monday and Friday");
    }

    private LocalTime validatePossibleHour(String hour) {
        LocalTime formattedHour;
        try {
            formattedHour = LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("You must enter a correct format for the schedule hour, eg: HH:mm");
        }

        if (!hourIsBetweenLimits(formattedHour)) {
            throw new OutOfRangeHourException("You must enter an hour between 9 and 19");
        }

        return formattedHour;
    }

    public boolean differsByAnHour(Schedule otherSchedule) {
        LocalTime scheduleHour = otherSchedule.hour;
        return weekDay.equals(otherSchedule.weekDay) && Math.abs(hour.getHour() - scheduleHour.getHour()) == 1 && hour.getMinute() == scheduleHour.getMinute();
    }

    private boolean hourIsBetweenLimits(LocalTime hour) {
        return hour.getHour() >= 9 && hour.getHour() <= 19;
    }
}
