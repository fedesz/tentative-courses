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

    def "If all students were assigned, they should have the property hasCourse in true"() {
        given:
        List<Teacher> teachers = mockTeachers()
        List<Student> students = mockStudents()

        when:
        tentativeCoursesGenerator.generateTentativeCourses(teachers, students)

        then:
        assert students.stream().anyMatch(
                {
                    student -> student.hasCourse()
                }
        )
    }

    def "If there is only one teacher who has 2 available schedules, and 2 students that match each schedule, the generator should create 2 courses"() {
        given:
        List<Teacher> teachers = [new Teacher("The teacher", [new Schedule(DayOfWeek.TUESDAY, "13:00"), new Schedule(DayOfWeek.FRIDAY, "13:00")])]
        List<Student> students = [
                new Student("The first student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.TUESDAY, "13:00")]),
                new Student("The second student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.FRIDAY, "13:00")])
        ]

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)

        then:
        assert courses.size() == 2
        assert teachers[0].availableDays[0] == courses[0].schedule
        assert teachers[0].availableDays[1] == courses[1].schedule
    }

    def "If 2 students require the same day differing by an hour, and both need GROUP course, they should be together"() {
        given:
        List<Teacher> teachers = [new Teacher("The teacher", [new Schedule(DayOfWeek.TUESDAY, "13:00"), new Schedule(DayOfWeek.FRIDAY, "13:00")])]
        List<Student> students = [
                new Student("The first student", CourseModality.GROUP, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.TUESDAY, "13:00")]),
                new Student("The second student", CourseModality.GROUP, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.TUESDAY, "14:00")])
        ]

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)

        then:
        assert courses.size() == 1
        assert students == courses[0].students
    }

    def "If 2 students require the same schedule, and both need INDIVIDUAL course, one of them should be rejected"() {
        given:
        List<Teacher> teachers = [new Teacher("The teacher", [new Schedule(DayOfWeek.TUESDAY, "13:00"), new Schedule(DayOfWeek.FRIDAY, "13:00")])]
        List<Student> students = [
                new Student("The first student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.FRIDAY, "13:00")]),
                new Student("The second student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.FRIDAY, "13:00")])
        ]

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)

        then:
        assert courses.size() == 1
        assert tentativeCoursesGenerator.rejectedStudents.size() == 1
    }

    def "If students and teachers schedules do not match, the generator should return an empty courses list and all students should be rejected"() {
        given:
        List<Teacher> teachers = [new Teacher("The teacher", [new Schedule(DayOfWeek.FRIDAY, "13:00")])]
        List<Student> students = [
                new Student("The first student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.TUESDAY, "13:00")]),
                new Student("The second student", CourseModality.INDIVIDUAL, KnowledgeLevel.ADVANCED, [new Schedule(DayOfWeek.WEDNESDAY, "13:00")])
        ]

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)

        then:
        assert courses.isEmpty()
        assert students == tentativeCoursesGenerator.rejectedStudents
    }

    def "If there is only one possible group course and 8 candidates for the same course, the generator should reject 2 candidates"() {
        given:
        def schedule = new Schedule(DayOfWeek.TUESDAY, "12:30")
        List<Teacher> teachers = [new Teacher("The teacher", [schedule])]
        List<Student> students = [
                new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Pedro Gonzalez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Anibal Moreno", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Jose Montoto", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Juan Lopez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Alberto Fernandez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Esteban Quito", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
                new Student("Elena Nito", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.TUESDAY, "12:30")]),
        ]

        when:
        def courses = tentativeCoursesGenerator.generateTentativeCourses(teachers, students)
        def rejectedStudents = tentativeCoursesGenerator.rejectedStudents

        then:
        assert courses.size() == 1
        assert courses[0].students.size() == 6
        assert schedule == courses[0].schedule
        assert rejectedStudents.size() == 2
    }

    private List<Teacher> mockTeachers() {
        Teacher teacher = new Teacher("Maria Gomez", [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Teacher teacher1 = new Teacher("Maria Perez", [new Schedule(DayOfWeek.MONDAY, "12:30")])
        [teacher, teacher1]
    }

    private List<Student> mockStudents() {
        Student student = new Student("Juan Perez", CourseModality.INDIVIDUAL, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student1 = new Student("Juan Gomez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student2 = new Student("Pedro Gonzalez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student3 = new Student("Anibal Moreno", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student4 = new Student("Jose Montoto", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student5 = new Student("Juan Lopez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        Student student6 = new Student("Alberto Fernandez", CourseModality.GROUP, KnowledgeLevel.BEGINNER, [new Schedule(DayOfWeek.MONDAY, "12:30")])
        [student, student1, student2, student3, student4, student5, student6]
    }
}