package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;

public class InstructorEditCourseActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCode, etName;
    Button btnEdit;
    String courseCode;

    EditText etDays, etHours, etCapacity, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit_course);


        etCode = findViewById(R.id.etCode);
        etName = findViewById(R.id.etName);
        btnEdit = findViewById(R.id.btnEdit);
        etDays = findViewById(R.id.etDays);
        etHours = findViewById(R.id.etHours);
        etCapacity = findViewById(R.id.etCapacity);
        etDescription = findViewById(R.id.etDescription);

        btnEdit.setOnClickListener(this);

        courseCode = getIntent().getStringExtra("code");
        Course course = Database.getInstance().getCourse(courseCode);
        etCode.setText(course.getCourseCode());
        etName.setText(course.getCourseName());

        etDays.setText(course.getDays());
        etHours.setText(course.getHours());
        etCapacity.setText("" + course.getCapacity());
        etDescription.setText(course.getDescription());
    }

    @Override
    public void onClick(View v) {
        if (v == btnEdit) {
            String days = etDays.getText().toString().trim();
            String hours = etHours.getText().toString().trim();
            String capacityStr = etCapacity.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (days.isEmpty()) {
                Toast.makeText(this, "course days is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if (hours.isEmpty()) {
                Toast.makeText(this, "course hours is empty", Toast.LENGTH_LONG).show();
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
            course.setHours(hours);
            course.setCapacity(capacity);
            course.setDescription(description);
            Database.getInstance().save(this);

            finish();
        }
    }
}