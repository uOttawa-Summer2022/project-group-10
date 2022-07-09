package com.app.coursebooking.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    public static Database instance;

    private Admin admin;
    private List<Instructor> instructors;
    private List<Student> students;
    private List<Course> courses;

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

        /*
        instructors.add(new Instructor("instructor1", "123", "instructor1", "instructor1", "instructor1@email.com",
                "1234567"));
        students.add(new Student("student1", "123", "student1", "student1", "student1@email.com",
                "1234567"));
        */

        courses.add(new Course("HIS101", "History"));
    }

    public static void load(Context context){
        try {
            File file = context.getFilesDir();
            FileInputStream fileInputStream = new FileInputStream(new File(file, "booking.dat"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            instance = (Database) objectInputStream.readObject();
            //Log.e("debug-load", instance.getCourses().size()+"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(Context context){
        try {
            File file = context.getFilesDir();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, "booking.dat"));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();

            //Log.e("debug-save", instance.getCourses().size()+"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public boolean signUp(User user) {
        if (user instanceof Instructor) {
           return instructors.add((Instructor) user);
        }
        if (user instanceof Student) {
            return students.add((Student) user);
        }
        return false;
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

    public boolean addInstructor(Instructor instructor) {
        if(getUser(instructor.getUsername()) != null){
            return false;
        }

        instructors.add(instructor);
        return true;
    }

    public boolean addStudent(Student student) {
        if(getUser(student.getUsername()) != null){
            return false;
        }


        students.add(student);
        return true;
    }

    public boolean addCourse(Course course) {
        if(getCourse(course.getCourseCode()) != null){
            return false;
        }

        courses.add(course);
        return true;
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

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public void removeInstructor(Instructor instructor) {
        instructors.remove(instructor);
    }

    public Course getCourse(String code) {
        for (Course course : courses
        ) {
            if (course.getCourseCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    public List<Course> searchCourse(String key) {
        List<Course> result = new ArrayList<>();
        key = key.toLowerCase();

        for (Course course : courses) {
            if (course.getCourseCode().toLowerCase().contains(key) || course.getCourseName().toLowerCase().contains(key)) {
                result.add(course);
            }
        }

        return result;
    }

}
