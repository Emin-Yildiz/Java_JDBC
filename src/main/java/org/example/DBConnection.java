package org.example;

import org.example.Repos.StudentRepo;
import org.example.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DBConnection implements StudentRepo {

    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/student";
    private String user = "username";
    private String password = "userpassword";

    public void connect(){
        try {
            connection = DriverManager.getConnection(url,user,password);
            if (connection != null)
                System.out.println("Db connection succes");
        } catch (SQLException e) {
            System.out.println("Db connection failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertStudent(Student student){
        String sql = "INSERT INTO student (id, name, school) VALUES (?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,student.getId());
            statement.setString(2,student.getName());
            statement.setString(3,student.getSchool());

            int query = statement.executeUpdate();

            if(query > 0){
                System.out.println("New student added");
            }
        } catch (SQLException e) {
            System.out.println("New student added failed");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getAllStudent(){
        ArrayList<Student> studentList = new ArrayList<>();

        String sql = "SELECT * FROM student";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                Student student = new Student(result.getInt(1),result.getString(2),result.getString(3));
                studentList.add(student);
            }
            displayStudentList(studentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Student getStudentById(int id){
        ResultSet result;
        Student student = null;
        String sql = "SELECT * FROM student WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            result = statement.executeQuery();

            while (result.next()){
                student = new Student(result.getInt(1),result.getString(2),result.getString(3));
                displayStudentById(student);

            }
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(int id){

        String sql = "DELETE FROM student WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            int result = statement.executeUpdate();

            if(result > 0){
                System.out.println("Student deleted (id: " + id + ")");
            }else {
                System.out.println("Deleted failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStudent(int id, Student newStudent){
        Optional<Student> student = Optional.ofNullable(getStudentById(2));

        String sql = "UPDATE student SET name=?, school=? WHERE id=?";

        if (student.isPresent()){
            Student foundStudent = student.get();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(3,foundStudent.getId());
                statement.setString(1,newStudent.getName());
                statement.setString(2, newStudent.getSchool());

                int result = statement.executeUpdate();
                if (result > 0){
                    System.out.println("Update succes");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void displayStudentList(ArrayList<Student> studentList) {

        studentList.forEach((s) -> {
            System.out.println("id: " + s.getId() + ", Student Name: " + s.getName()+ ", Student School: " + s.getSchool());
        });

    }

    private void displayStudentById(Student student){
        System.out.println("id: " + student.getId() + ", Student Name: " + student.getName()+ ", Student School: " + student.getSchool());
    }

    public void close(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
