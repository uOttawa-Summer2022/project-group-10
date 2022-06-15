package com.app.coursebooking.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final int INSTRUCTOR = 1;
    public static final int STUDENT = 2;

    private Admin admin;
    private List<Instructor> instructors;
    private List<Student> students;
    private List<Course> courses;

    public Database() {
        admin = new Admin();
        instructors = new ArrayList<>();
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public User getUser(String username) {
        if (username.equals(admin.getUsername())) {
            return admin;
        }

        for (Instructor instructor : instructors) {
            if (instructor.getUsername().equals(username)) {
                return instructor;
            }
        }

        for (Student student : students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }

        return null;
    }

    public void signUp(User user) {
        if (user instanceof Instructor) {
            instructors.add((Instructor) user);
        }
        if (user instanceof Student) {
            students.add((Student) user);
        }
    }

    public User login(String username, String password) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
