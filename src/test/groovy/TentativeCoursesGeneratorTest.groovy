import generators.TentativeCoursesGenerator
import models.*
import spock.lang.Specification

import java.time.DayOfWeek

class TentativeCoursesGeneratorTest extends Specification {
    TentativeCoursesGenerator tentativeCoursesGenerator = new TentativeCoursesGenerator()

    def "If the course generator is invoked with correct lists of students and teachers and match them schedules, it should be return a non empty list of tentative courses"() {
        given:
        List<Teacher> teachers = mockTeachers()
        List<Student> students = mockStudents()

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)

        then:
        assert !courses.isEmpty()
        assert courses.size() == 2
    }

    def "If there is only 1 teacher available with a single schedule, and 2 students with individual modality at the same schedule, 1 course will be generated and the other student will be rejected"() {
        given:
        List<Teacher> teachers = [new Teacher("The teacher", [new Schedule(DayOfWeek.TUESDAY, "13:00")])]
        List<Student> students = [
                new Student("The first student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.TUESDAY, "13:00")]),
                new Student("The second student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.TUESDAY, "13:00")])
        ]

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)
        def rejectedStudents = tentativeCoursesGenerator.rejectedStudents

        then:
        def course = courses[0]
        def courseSchedule = course.schedule
        assert !courses.isEmpty()
        assert courses.size() == 1
        assert course.teacher.availableDays.contains(courseSchedule)
        assert course.students[0].availableDays.contains(courseSchedule)
        assert !rejectedStudents.isEmpty()
        assert rejectedStudents[0].availableDays.contains(courseSchedule)
    }

    def "If the are no teachers or students, the generator should return an empty list of courses"() {
        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses([], [])

        then:
        assert courses.isEmpty()
    }

    /*def "If all students were assigned, they should has the property hasCourse in true"() {
        given:
        List<Teacher> teachers = mockTeachers()
        List<Student> students = mockStudents()

        when:


        then:
    }*/

    private List<Teacher> mockTeachers() {
        Teacher teacher = new Teacher("Maria Gomez", [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Teacher teacher1 = new Teacher("Maria Perez", [new Schedule(DayOfWeek.MONDAY, "12:30")])
        [teacher, teacher1]
    }

    private List<Student> mockStudents() {
        Student student = new Student("Juan Perez", CourseModality.INDIVIDUAL, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student1 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student2 = new Student("Pedro Gonzalez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")])
        Student student3 = new Student("Anibal Moreno", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student4 = new Student("Jose Montoto", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student5 = new Student("Juan Lopez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student6 = new Student("Alberto Fernandez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        [student, student1, student2, student3, student4, student5, student6]
    }
}