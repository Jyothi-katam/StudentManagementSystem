package com.agent.studentmanagementsystem.service;

import com.agent.studentmanagementsystem.model.Student;
import com.agent.studentmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JavaMailSender mailSender;


    public ResponseEntity<?> addStudent(Student student)
    {
        Boolean  isFind=studentRepository.existsByPhone(student.getPhone());
        boolean isExist=studentRepository.existsByEmail(student.getEmail());
        if(isFind)
        {
            return new ResponseEntity<>("Student already exists", HttpStatus.CONFLICT);
        }
        else if (isExist)
        {
            return new ResponseEntity<>("Student already exists", HttpStatus.CONFLICT);

        } else
        {
            Student newStudent = new Student();
            newStudent.setName(student.getName());
            newStudent.setAge(student.getAge());
            newStudent.setGender(student.getGender());
            newStudent.setEmail(student.getEmail());
            newStudent.setAddress(student.getAddress());
            newStudent.setPhone(student.getPhone());
            newStudent.setCourse(student.getCourse());
            studentRepository.save(newStudent);

            SimpleMailMessage message = getSimpleMailMessage(student);
            mailSender.send(message);

            return ResponseEntity.ok(newStudent);
        }

    }

    private static SimpleMailMessage getSimpleMailMessage(Student student) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(student.getEmail());
        message.setSubject("Student Registration Success");
        message.setText("Student Registered Successfully\n"+
                "The below are the student Details: \n"+
                "Name: "+ student.getName()+"\n"+
                "Phone: "+ student.getPhone()+"\n"+
                "Gender: "+ student.getGender()+"\n"+
                "Address: "+ student.getAddress()+"\n"+
                "Course: "+ student.getCourse());
        return message;
    }

    public ResponseEntity<List<Student>> getAllStudents()
    {
        List<Student> students=studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    public ResponseEntity<?> findStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);;
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with ID: " + id);
        }
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<?> deleteStudentById(Long id)
    {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return ResponseEntity.ok("Student deleted successfully");
        }
        else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateStudentById(Long id, Student student)
    {
        Student s1=studentRepository.findById(id).orElse(null);
//        Student newStudent=new Student();
//        newStudent.setName(student.getName());
        s1.setName(student.getName());
        s1.setAge(student.getAge());
        s1.setGender(student.getGender());
        s1.setEmail(student.getEmail());
        student.setPhone(student.getPhone());
        student.setCourse(student.getCourse());
        s1.setAddress(student.getAddress());
        studentRepository.save(s1);
        return ResponseEntity.ok("Student updated successfully");

    }
}
