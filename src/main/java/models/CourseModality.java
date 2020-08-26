package models;

public enum CourseModality {
    GROUP(6),
    INDIVIDUAL(1);

    private int maxCapacity;

    CourseModality(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
