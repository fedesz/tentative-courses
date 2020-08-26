import exceptions.OutOfRangeHourException
import models.Schedule
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ScheduleTest extends Specification {
    def "If i try to create a schedule between 9 and 19, and a day between monday and friday, schedule's constructor should create the object"() {
        given:
        def weekDay = DayOfWeek.FRIDAY
        def hour = "09:00"

        when:
        def schedule = new Schedule(weekDay, hour)

        then:
        assert schedule instanceof Schedule
        assert weekDay == schedule.weekDay
        assert LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm")) == schedule.hour
    }
    def "If i try to create a schedule earlier than 9 and later than 19, schedule's constructor should throw an exception"() {
        when:
        new Schedule(DayOfWeek.FRIDAY, "08:00")

        then:
        thrown(OutOfRangeHourException)
    }

    def "If i try to create a schedule on saturday or sunday, schedule's constructor should throw an exception"() {
        when:
        new Schedule(DayOfWeek.SATURDAY, "10:00")

        then:
        thrown(IllegalArgumentException)
    }
}
