package models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private Teacher teacher;
    private CourseModality modality;
    private KnowledgeLevel knowledgeLevel;
    private Schedule schedule;
    private final List<Student> students;
    private int maxCapacity;

    public Course(Teacher teacher, Schedule schedule) {
        this.teacher = teacher;
        this.schedule = schedule;
        this.students = new ArrayList<>();
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setModality(CourseModality modality) {
        this.modality = modality;
        this.maxCapacity = modality.getMaxCapacity();
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
        boolean canAddStudent = validateCourseAvailability() && accomplishConditions(newStudent);
        if (canAddStudent) {
            if (students.isEmpty()) {
                setKnowledgeLevel(newStudent.getKnowledgeLevel());
                setModality(newStudent.getCourseModality());
            }

            this.students.add(newStudent);
            newStudent.setHasCourse(true);
        }
    }

    public boolean accomplishConditions(Student student) {
        return !student.hasCourse() && student.getCourseModality().equals(modality) && student.getKnowledgeLevel().equals(knowledgeLevel);
    }

    private boolean validateCourseAvailability() {
        return this.students.size() < this.maxCapacity;
    }
}
