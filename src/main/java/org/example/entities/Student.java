package org.example.entities;

public class Student {

    private int id;
    private String name;
    private String school;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Student(int id, String name, String school){
        this.id = id;
        this.name = name;
        this.school = school;
    }

}
