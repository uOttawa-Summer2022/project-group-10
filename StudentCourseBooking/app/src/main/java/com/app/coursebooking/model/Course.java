package com.app.coursebooking.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course implements Serializable {
    private String courseCode;
    private String courseName;

    private String instructorUsername;
    private String days;
    private String hours;
    private String description;
    private int capacity;

    private List<String> students = new ArrayList<>();

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;

        days = "";
        hours = " - ";
        description = "";
        capacity = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorUsername() {
        return instructorUsername;
    }

    public void setInstructorUsername(String instructorUsername) {
        this.instructorUsername = instructorUsername;

        if(instructorUsername == null){
            days = "";
            hours = " - ";
            description = "";
            capacity = 0;
            students = new ArrayList<>();
        }
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public String getHoursStart() {
        return hours.split("-")[0].trim();
    }

    public String getHoursEnd() {
        return hours.split("-")[1].trim();
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getStudents() {
        return students;
    }

    public void reduceCapacity() {
        capacity--;
    }

    public void addCapacity() {
        capacity++;
    }

}
