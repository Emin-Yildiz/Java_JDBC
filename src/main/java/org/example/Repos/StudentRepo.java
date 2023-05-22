package org.example.Repos;

import org.example.entities.Student;

public interface StudentRepo {

    public void insertStudent(Student student);

    public void getAllStudent();

    public Student getStudentById(int id);

    public void deleteStudent(int id);

    public void updateStudent(int id, Student newStudent);

}
