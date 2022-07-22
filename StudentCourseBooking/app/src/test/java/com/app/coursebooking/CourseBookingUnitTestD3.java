package com.app.coursebooking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.app.coursebooking.model.Validator;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CourseBookingUnitTestD3 {

    @Test
    public void testIsValidateDays() {
        assertTrue(Validator.isValidDays("Monday"));
        assertFalse(Validator.isValidDays("day"));
    }

    @Test
    public void testValidateDays() {
        assertEquals(0, Validator.getDaysPosition("Monday"));
        assertEquals(5, Validator.getDaysPosition("Saturday"));
    }

    @Test
    public void testConflictTime() {
        assertTrue(Validator.isConflict("14:00-15:00", "14:30-16:00"));
        assertTrue(Validator.isConflict("14:00-15:00", "13:30-16:00"));
    }

    @Test
    public void testValidateTime() {
        assertTrue(Validator.isValidTime("14:00"));
        assertFalse(Validator.isValidTime("25:30"));
    }

    @Test
    public void testToTime() {
        assertEquals(60, Validator.toTime("1:00"));
    }
}