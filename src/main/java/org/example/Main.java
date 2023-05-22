package org.example;

import org.example.entities.Student;

public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        Student student = new Student(4,"Beyza","cü");
        db.connect();
        //db.insertStudent(student);
        //db.getAllStudent();
        //db.getStudentById(2);
        //db.deleteStudent(1);
        db.updateStudent(2,student);
        db.close();
    }
}