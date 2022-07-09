package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.coursebooking.model.Admin;
import com.app.coursebooking.model.Student;

public class AdminWelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvWelcome;

    Button btnCourse, btnInstructor, btnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnCourse = findViewById(R.id.btnCourse);
        btnInstructor = findViewById(R.id.btnInstructor);
        btnStudent = findViewById(R.id.btnStudent);

        Admin admin = (Admin) getIntent().getSerializableExtra("user");

        String msg = "Welcome '" + admin.getUsername() + "'! You are logged in as 'Admin'";
        tvWelcome.setText(msg);

        btnCourse.setOnClickListener(this);
        btnInstructor.setOnClickListener(this);
        btnStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnStudent){
            Intent intent = new Intent(this, StudentListActivity.class);
            startActivity(intent);
        }
        if(v == btnInstructor){
            Intent intent = new Intent(this, InstructorListActivity.class);
            startActivity(intent);
        }
        if(v == btnCourse){
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        }
    }
}