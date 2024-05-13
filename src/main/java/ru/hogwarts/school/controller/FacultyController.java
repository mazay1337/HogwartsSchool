package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RequestMapping("/faculty")
@RestController
public class FacultyController {

    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }



    @PostMapping
    @Operation(summary = "Создание факультета")
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение факультета")
    public Faculty update(@PathVariable long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление факультета")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Нахождение факультета")
    public Faculty get(@PathVariable long id) {
        return facultyService.get(id);
    }

    @GetMapping(params = "color")
    public List<Faculty> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping(params = "colorOrName")
    public List<Faculty> findNameOrColor(@RequestParam String colorOrName) {
        return facultyService.findNameOrColor(colorOrName);
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Получение студентов по факультету")
    public List<Student> findStudents(@PathVariable long id) {
        return facultyService.findStudents(id);
    }
}
