package com.app.coursebooking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CourseBookingUnitTestD2 {
    @Before
    public void init(){
        Database.instance = null;
    }

    @Test
    public void testAddCourse() {
        assertTrue(Database.getInstance().addCourse(new Course("C01", "C01_Name")));
        assertFalse(Database.getInstance().addCourse(new Course("C01", "C01_Name")));

        assertTrue(Database.getInstance().searchCourse("C01").size() > 0);
    }

    @Test
    public void testAddStudent() {
        assertTrue(Database.getInstance().signUp(new Student(
                "S01", "123", "S01", "S01_Name", "email", "phone")));

        assertTrue(Database.getInstance().login("S01", "123") != null);
    }

    @Test
    public void testAddInstructor() {
        assertTrue(Database.getInstance().signUp(new Instructor(
                "I01", "123", "I01", "I01_Name", "email", "phone")));

        assertTrue(Database.getInstance().login("I01", "123") != null);
        assertTrue(Database.getInstance().login("I01", "123") instanceof  Instructor);
    }

    @Test
    public void testAssign() {
        Database.getInstance().addCourse(new Course("C01", "C01_Name"));
     Database.getInstance().signUp(new Instructor(
                "I01", "123", "I01", "I01_Name", "email", "phone"));

        Instructor instructor = (Instructor) Database.getInstance().getUser("I01");
        Course course = Database.getInstance().getCourse("C01");
        course.setDays("Monday");

        course.setInstructorUsername(instructor.getUsername());

        assertFalse(course.getDays().isEmpty());
    }


    @Test
    public void testUnAssign() {
        Database.getInstance().addCourse(new Course("C01", "C01_Name"));
        Database.getInstance().signUp(new Instructor(
                "I01", "123", "I01", "I01_Name", "email", "phone"));

        Instructor instructor = (Instructor) Database.getInstance().getUser("I01");
        Course course = Database.getInstance().getCourse("C01");
        course.setDays("Monday");

        course.setInstructorUsername(null);

        assertTrue(course.getDays().isEmpty());
    }

}