package com.agent.studentmanagementsystem.controller;

import com.agent.studentmanagementsystem.model.Student;
import com.agent.studentmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<?> addStudent(@RequestBody Student student)
    {
        return studentService.addStudent(student);
    }

    @GetMapping("/listofstudents")
    public ResponseEntity<List<Student>> getAllStudents()
    {
        return studentService.getAllStudents();
    }

    @GetMapping("/findBy{id}")
    public ResponseEntity<?> findStudentById(@PathVariable Long id)
    {
        return studentService.findStudentById(id);
    }

    @DeleteMapping("deleteBy{id}")
    public ResponseEntity<?> deleStudentbyId(@PathVariable Long id)
    {
        return studentService.deleteStudentById(id);
    }

    @PutMapping("/updateBy{id}")
    public ResponseEntity<?> updateByStudentId(@PathVariable Long id, @RequestBody Student student)
    {
        return studentService.updateStudentById(id,student);
    }
}

