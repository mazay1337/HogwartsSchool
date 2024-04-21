package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;


@Tag(name = "Студенты", description = "Эндпойнты для работы со студентами")
@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    @Operation(summary = "Создание студента")
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение студента")
    public Student update(@PathVariable long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable long id) {
        return studentService.get(id);
    }

    @GetMapping(params = "age")
    public List<Student> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public List<Student> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/faculty")
    public Faculty findFaculty(@PathVariable long id) {
        return studentService.findFaculty(id);
    }
}
