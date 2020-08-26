package models;

import java.util.List;

public class Teacher {
    private final String name;
    private List<Schedule> availableDays;

    public Teacher(String name, List<Schedule> availableDays) {
        this.name = name;
        this.availableDays = availableDays;
    }

    public void setAvailableDays(List<Schedule> availableDays) {
        this.availableDays = availableDays;
    }

    public String getName() {
        return this.name;
    }

    public List<Schedule> getAvailableDays() {
        return this.availableDays;
    }
}
