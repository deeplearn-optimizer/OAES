package com.example.org.service;

import com.example.org.bean.Student;
import com.example.org.bean.Users;
import com.example.org.dao.StudentDAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentService {
    StudentDAO dao = new StudentDAO();

    public static final Logger logger = Logger.getLogger(StudentService.class.toString());

    public boolean addStudent(Student student) {
        logger.log(Level.INFO, "addStudent invoked for student " + student.getStudent_id());
        return dao.addStudent(student);
    }
    public List<Student> getAllStudents() {
        return dao.getAllStudents();
    }
    public Student getStudentById(int id) {
        return dao.getStudentById(id);
    }
    public boolean updateStudent(Student student){return dao.updateStudent(student);}
    public boolean deleteStudent(Student student){
        logger.log(Level.INFO, "deleteStudent accessed for student " + student.getStudent_id());
        return dao.deleteStudent(student);
    }
    public List<Student> getStudents(){
        return getAllStudents();
    }

    public String uploadStudent(String first_name,
                                  String middle_name,
                                  String last_name,
                                  String email,
                                  String contact_number,
                                  String enrollment_number,
                                  String address,
                                  String username,
                                  String password) {

        System.out.println("here!");

        logger.log(Level.INFO, "uploadStudent has invoked for student username with " + username);

        StudentService service = new StudentService();
        if((username.length() < 8) || (password.length() < 8)) {
            return new String("Error Creating student!");
        }

        AuthenticationService service1 = new AuthenticationService();
        service1.signUp(username, password, "student");

        Student student = Student.factory();
        student.setFirst_name(first_name);
        student.setLast_name(last_name);
        student.setAddress(address);
        student.setContact_number(contact_number);
        student.setEnrollment_number(enrollment_number);
        student.setUsername(username);
        student.setPassword(password);
        student.setEmail(email);
        student.setMiddle_name(middle_name);

        System.out.println("here!");
        boolean result = service.addStudent(student);
        String n = new String("New Student Created!");
        if(!result)
            n = new String("Error Creating student!");
        return n;
    }

    public String deleteStudent(int id){
        Student student = getStudentById(id);
        AuthenticationService service = new AuthenticationService();
        try {
            service.deleteUser(student.getUsername(), student.getPassword());
        } catch (Exception e) {
            return new String("Student could not be deleted!");
        }
        boolean result = deleteStudent(student);
        String n = new String("Student Deleted Successfully!");
        if(!result)
            n = new String("Student could not be deleted!");
        return n;
    }

}
