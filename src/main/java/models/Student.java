package models;

import java.util.List;

public class Student {
    private final String name;
    private CourseModality courseModality;
    private KnowledgeLevel knowledgeLevel;
    private List<Schedule> availableSchedules;
    private boolean hasCourse;

    public Student(String name, CourseModality courseModality, KnowledgeLevel knowledgeLevel, List<Schedule> availableSchedules) {
        this.name = name;
        this.courseModality = courseModality;
        this.knowledgeLevel = knowledgeLevel;
        this.availableSchedules = availableSchedules;
        this.hasCourse = false;
    }

    public void setCourseModality(CourseModality courseModality) {
        this.courseModality = courseModality;
    }

    public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    public void setAvailableDays(List<Schedule> availableSchedules) {
        this.availableSchedules = availableSchedules;
    }

    public void setHasCourse(boolean hasCourse) {
        this.hasCourse = hasCourse;
    }

    public String getName() {
        return name;
    }

    public CourseModality getCourseModality() {
        return courseModality;
    }

    public KnowledgeLevel getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public List<Schedule> getAvailableDays() {
        return availableSchedules;
    }

    public boolean hasCourse() {
        return hasCourse;
    }
}
