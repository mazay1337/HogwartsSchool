package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    public Student create(Student student) {
        logger.info("method create was invoked");
        student.setId(null);
        fillFaculty(student.getFaculty(), student);
        return studentRepository.save(student);
    }

    public Student update(long id, Student student) {
        logger.warn("method update was invoked with parameters id ={}, studentName = {}", id, student.getName());
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setName(student.getName());
                    oldStudent.setAge(student.getAge());
                    fillFaculty(student.getFaculty(), oldStudent);
                    return studentRepository.save(oldStudent);
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student delete(long id) {
        logger.warn("method delete was invoked with parameter id ={},", id);
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return student;
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student get(long id) {
        logger.debug("method get was invoked with parameters id ={}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public List<Student> findByAge(int age) {
        logger.info("method findByAge was invoked with parameter age ={}", age);
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.debug("method findByAgeBetween was invoked with parameters minAge ={}, maxAge = {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findFaculty(long id) {
        logger.debug("method findFaculty was invoked with parameters id ={}", id);
        return get(id).getFaculty();
    }

    private void fillFaculty(Faculty faculty, Student student) {
        logger.debug("method findByAgeBetween was invoked with parameters faculty ={}, student = {}", faculty, student);
        if (faculty != null && faculty.getId() != null) {
            Faculty facultyFromDb = facultyRepository.findById(faculty.getId())
                    .orElseThrow(() -> new FacultyNotFoundException(faculty.getId()));
            student.setFaculty(facultyFromDb);
        }
    }

    public int getCountOfStudents() {
        logger.debug("method getCountOfStudents was invoked");
        return studentRepository.getCountOfStudents();
    }

    public double getAverageAgeOfStudents() {
        logger.debug("method getAverageAgeOfStudents was invoked");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .summaryStatistics()
                .getAverage();
    }

    public List<Student> getLastNStudents(int count) {
        logger.debug("method getLastNStudents was invoked with parameter count ={}", count);
        return studentRepository.getLastNStudents(count);
    }

    public List<String> getNameOfStudentsWhichStartsWith(char startsWith) {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith(Character.toString(startsWith).toUpperCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    public void printParallel() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0, 6)).stream().toList();
        if (students.size() >= 6) {
            logger.info(students.get(0).getName());
            logger.info(students.get(1).getName());
            new Thread(() -> {
                logger.info(students.get(2).getName());
                logger.info(students.get(3).getName());
            }).start();
            new Thread(() -> {
                logger.info(students.get(4).getName());
                logger.info(students.get(5).getName());
            }).start();
        }
    }

    public void printSynchronized() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0, 6)).stream().toList();
        if (students.size() >= 6) {
            print(students.get(0));
            print(students.get(1));
            new Thread(() -> {
                print(students.get(2));
                print(students.get(3));
            }).start();
            new Thread(() -> {
                print(students.get(4));
                print(students.get(5));
            }).start();
        }
    }

    private synchronized void print(Student student) {
        logger.info(student.getName());
    }
}
