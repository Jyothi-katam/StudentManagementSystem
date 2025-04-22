package com.agent.studentmanagementsystem.repository;

import com.agent.studentmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{

    Boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
