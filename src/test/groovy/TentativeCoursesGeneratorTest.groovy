import generators.TentativeCoursesGenerator
import models.CourseModality
import models.KnowledgeLevel
import models.Schedule
import models.Student
import models.Teacher
import spock.lang.Specification

import java.time.DayOfWeek

class TentativeCoursesGeneratorTest extends Specification {
    def "If the course generator is invoked with correct lists of students and teachers, it should be return a list of tentative courses"() {
        given:
        TentativeCoursesGenerator tentativeCoursesGenerator = new TentativeCoursesGenerator()
        List<Teacher> teachers = mockTeachers()
        List<Student> students = mockStudents()

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)
        def rejectedStudents = tentativeCoursesGenerator.rejectedStudents

        then:
        assert courses.size() == 3
        assert rejectedStudents.size() == 1
    }

    private List<Teacher> mockTeachers() {
        Teacher teacher = new Teacher("Maria Gomez", [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Teacher teacher1 = new Teacher("Maria Perez", [new Schedule(DayOfWeek.MONDAY, "12:30"), new Schedule(DayOfWeek.MONDAY, "12:31")])
        [teacher, teacher1]
    }

    private List<Student> mockStudents() {
        Student student = new Student("Juan Perez", CourseModality.INDIVIDUAL, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student1 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student2 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student3 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student4 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student5 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:32")])
        Student student6 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student7 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:33"),
                                                                                                     new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student8 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student9 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:31")])
        [student, student1, student2, student3, student4, student5, student6, student7, student8, student9]
    }
}