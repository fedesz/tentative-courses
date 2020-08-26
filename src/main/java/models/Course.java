package models;

import exceptions.FullCourseException;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private Teacher teacher;
    private CourseModality modality;
    private KnowledgeLevel knowledgeLevel;
    private Schedule schedule;
    private final List<Student> students;
    private int maxCapacity;

    public Course(Teacher teacher, CourseModality modality, KnowledgeLevel knowledgeLevel, Schedule schedule) {
        this.teacher = teacher;
        this.modality = modality;
        this.knowledgeLevel = knowledgeLevel;
        this.schedule = schedule;
        this.maxCapacity = modality.getMaxCapacity();
        this.students = new ArrayList<>();
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setModality(CourseModality modality) {
        this.modality = modality;
    }

    public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public CourseModality getModality() {
        return modality;
    }

    public KnowledgeLevel getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Student> getStudents() {
        return students;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void addNewStudent(Student newStudent) {
        validateCourseAvailability();
        this.students.add(newStudent);
    }
    
    private void validateCourseAvailability() {
        if (isFull()) {
            throw new FullCourseException("This course is full, the student will be rejected.");
        }
    }
    
    private boolean isFull() {
        return this.students.size() < this.maxCapacity;
    }
}
