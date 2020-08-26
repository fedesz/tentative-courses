package generators;

import models.Course;
import models.Schedule;
import models.Student;
import models.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TentativeCoursesGenerator {
    private List<Student> rejectedStudents;

    public TentativeCoursesGenerator() {
    }

    public List<Student> getRejectedStudents() {
        return rejectedStudents;
    }

    public List<Course> generateTentativeCourses(List<Teacher> teachers, List<Student> students) {
        final List<Course> tentativeCourses = new ArrayList<>();
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
        final List<Student> possibleStudents = students.stream()
                .filter(student -> !student.hasCourse() && student.getAvailableDays().contains(schedule))
                .collect(Collectors.toList());

        final Optional<Student> optionalFirstStudent = possibleStudents.stream().findAny();
        if (optionalFirstStudent.isPresent()) {
            Student firstStudent = optionalFirstStudent.get();
            Course course = new Course(teacher, schedule);
            course.setKnowledgeLevel(firstStudent.getKnowledgeLevel());
            course.setModality(firstStudent.getCourseModality());
            course.addNewStudent(firstStudent);

            possibleStudents.stream()
                    .filter(course::accomplishConditions)
                    .forEach(course::addNewStudent);
            return course;
        }
        return null;
    }

    private boolean thereAreUnassignedStudents(List<Student> students) {
        return students.stream().anyMatch(student -> !student.hasCourse());
    }
}