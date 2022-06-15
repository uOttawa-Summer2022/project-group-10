package com.app.coursebooking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    public static final int INSTRUCTOR = 1;
    public static final int STUDENT = 2;

    private Admin admin;
    private List<Instructor> instructors;
    private List<Student> students;
    private List<Course> courses;

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {
        admin = new Admin();
        instructors = new ArrayList<>();
        students = new ArrayList<>();
        courses = new ArrayList<>();

        instructors.add(new Instructor("instructor1", "123", "instructor1", "instructor1", "instructor1@email.com",
                "1234567"));
        students.add(new Student("student1", "123", "student1", "student1", "student1@email.com",
                "1234567"));

        courses.add(new Course("HIS101", "History"));
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

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
