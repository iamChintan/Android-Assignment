package com.chintan.assignment2;

public class FormModel {

    // declaration of fields
    int id;
    String firstName;
    String lastName;
    String course;
    int credit;
    double marks;

    // parameterized constructor
    public FormModel(int id, String firstName, String lastName, String course, int credit, double marks) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.credit = credit;
        this.marks = marks;
    }

    // default constructor
    public FormModel() {
    }


    // getter setter of the fields
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }




}
