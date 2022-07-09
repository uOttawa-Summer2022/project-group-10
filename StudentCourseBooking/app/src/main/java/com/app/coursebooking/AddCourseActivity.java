package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCode, etName;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        etCode = findViewById(R.id.etCode);
        etName = findViewById(R.id.etName);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd){
            String code = etCode.getText().toString().trim();
            String name = etName.getText().toString().trim();


            if (code.isEmpty()) {
                Toast.makeText(this, "course code is empty", Toast.LENGTH_LONG).show();
                return;
            }

            if(Database.getInstance().getCourse(code) != null){
                Toast.makeText(this, "course code already exists", Toast.LENGTH_LONG).show();
                return;
            }
            if (name.isEmpty()) {
                Toast.makeText(this, "course name is empty", Toast.LENGTH_LONG).show();
                return;
            }

            Database.getInstance().addCourse(new Course(code, name));
            Database.getInstance().save(this);
            finish();
        }
    }
}