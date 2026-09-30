package org.aman;

import org.aman.model.Student;
import org.aman.repository.StudentRepository;

public class Main {
    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository();
        studentRepository.createStudent(new Student("Anshu", "anshu@gmail.com", 25));
        // studentRepository.updateStudent();
        // studentRepository.deleteStudent();
        // studentRepository.getStudentById();
    }
}

/*
    In order to interact with our database, we need three things:
     -> Url,
     -> Username,
     -> Password
*/