package generators;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TentativeCoursesGenerator {
    private List<Course> tentativeCourses;
    private List<Student> rejectedStudents;

    public List<Student> getRejectedStudents() {
        return rejectedStudents;
    }

    public List<Course> generateTentativeCourses(List<Teacher> teachers, List<Student> students) {
        tentativeCourses = new ArrayList<>();
        teachers.forEach(teacher -> {
            if (thereAreUnassignedStudents(students)) {
                teacher.getAvailableDays()
                        .forEach(schedule -> {
                            final Course course = createCourse(teacher, schedule, students);
                            if (course != null) {
                                tentativeCourses.add(course);
                            }
                        });
            }
        });

        rejectedStudents = students.stream().filter(student -> !student.hasCourse()).collect(Collectors.toList());

        return tentativeCourses;
    }

    private Course createCourse(Teacher teacher, Schedule schedule, List<Student> students) {
        final List<Student> possibleStudents = filterPossibleStudents(schedule, students);

        if (!possibleStudents.isEmpty()) {
            Course course = new Course(teacher, schedule);
            possibleStudents.forEach(course::addNewStudent);
            return course;
        }
        return null;
    }

    private List<Student> filterPossibleStudents(Schedule schedule, List<Student> students) {
        return students.stream()
                .filter(student ->
                        !student.hasCourse() && (validateStudentGroupModalityAndScheduleDifferingByAnHour(schedule, student)
                                || student.getAvailableDays().contains(schedule))
                ).collect(Collectors.toList());
    }

    private boolean validateStudentGroupModalityAndScheduleDifferingByAnHour(Schedule schedule, Student student) {
        return student.getCourseModality().equals(CourseModality.GROUP) && student.getAvailableDays().stream().anyMatch(schedule::differsByAnHour);
    }

    private boolean thereAreUnassignedStudents(List<Student> students) {
        return students.stream().anyMatch(student -> !student.hasCourse());
    }
}
