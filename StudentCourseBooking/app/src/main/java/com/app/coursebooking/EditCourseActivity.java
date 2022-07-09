package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;

public class EditCourseActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCode, etName;
    Button btnEdit;
    String courseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        etCode = findViewById(R.id.etCode);
        etName = findViewById(R.id.etName);
        btnEdit = findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(this);

        courseCode = getIntent().getStringExtra("code");
        Course course = Database.getInstance().getCourse(courseCode);
        etCode.setText(course.getCourseCode());
        etName.setText(course.getCourseName());
    }

    @Override
    public void onClick(View v) {
        if(v == btnEdit){
            String code = etCode.getText().toString().trim();
            String name = etName.getText().toString().trim();

            if (code.isEmpty()) {
                Toast.makeText(this, "course code is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if (name.isEmpty()) {
                Toast.makeText(this, "course name is empty", Toast.LENGTH_LONG).show();
                return;
            }

            Course course = Database.getInstance().getCourse(courseCode);
            course.setCourseCode(code);
            course.setCourseName(name);
            Database.getInstance().save(this);

            finish();
        }
    }
}