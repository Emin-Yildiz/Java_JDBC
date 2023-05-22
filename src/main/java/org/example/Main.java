package org.example;

import org.example.entities.Student;

public class Main {
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        Student student = new Student(5,"Beyza","suşehri");
        db.connect();
        db.insertStudent(student);
        System.out.println("---------------------");
        db.getAllStudent();
        System.out.println("---------------------");
        db.getStudentById(5);
        System.out.println("---------------------");
        db.deleteStudent(3);
        System.out.println("---------------------");
        db.updateStudent(2,student);
        System.out.println("---------------------");
        db.close();
    }
}