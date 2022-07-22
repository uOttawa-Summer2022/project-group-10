package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Validator;

import java.util.List;

public class InstructorEditCourseActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCode, etName;
    Button btnEdit;
    String courseCode;

    Spinner etDays;
    EditText etHoursStart, etHoursEnd, etCapacity, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit_course);


        etCode = findViewById(R.id.etCode);
        etName = findViewById(R.id.etName);
        btnEdit = findViewById(R.id.btnEdit);
        etDays = findViewById(R.id.etDays);
        etHoursStart = findViewById(R.id.etHoursStart);
        etHoursEnd = findViewById(R.id.etHoursEnd);
        etCapacity = findViewById(R.id.etCapacity);
        etDescription = findViewById(R.id.etDescription);

        btnEdit.setOnClickListener(this);

        courseCode = getIntent().getStringExtra("code");
        Course course = Database.getInstance().getCourse(courseCode);
        etCode.setText(course.getCourseCode());
        etName.setText(course.getCourseName());

        etDays.setSelection(Validator.getDaysPosition(course.getDays()));
        etHoursStart.setText(course.getHoursStart());
        etHoursEnd.setText(course.getHoursEnd());
        etCapacity.setText("" + course.getCapacity());
        etDescription.setText(course.getDescription());
    }

    @Override
    public void onClick(View v) {
        if (v == btnEdit) {
            String days = etDays.getSelectedItem().toString().trim();
            String hoursStart = etHoursStart.getText().toString().trim();
            String hoursEnd = etHoursEnd.getText().toString().trim();
            String capacityStr = etCapacity.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (days.isEmpty()) {
                Toast.makeText(this, "course days is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if (hoursStart.isEmpty()) {
                Toast.makeText(this, "course hours start is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if(!Validator.isValidTime(hoursStart)){
                Toast.makeText(this, "course hours start is invalid", Toast.LENGTH_LONG).show();
                return;
            }

            if (hoursEnd.isEmpty()) {
                Toast.makeText(this, "course hours end is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if(!Validator.isValidTime(hoursEnd)){
                Toast.makeText(this, "course hours end is invalid", Toast.LENGTH_LONG).show();
                return;
            }

            int capacity = 0;
            try {
                capacity = Integer.parseInt(capacityStr);
                if (capacity <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "capacity should be a positive integer", Toast.LENGTH_LONG).show();
                return;
            }

            Course course = Database.getInstance().getCourse(courseCode);
            course.setDays(days);
            course.setHours(hoursStart+"-"+hoursEnd);
            course.setCapacity(capacity);
            course.setDescription(description);
            Database.getInstance().save(this);

            finish();
        }
    }
}