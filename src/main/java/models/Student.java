package models;

import java.util.List;

public class Student {
    private final String name;
    private CourseModality courseModality;
    private KnowledgeLevel knowledgeLevel;
    private List<Schedule> availableSchedules;

    public Student(String name, CourseModality courseModality, KnowledgeLevel knowledgeLevel, List<Schedule> availableSchedules) {
        this.name = name;
        this.courseModality = courseModality;
        this.knowledgeLevel = knowledgeLevel;
        this.availableSchedules = availableSchedules;
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
}
