package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;
import com.app.coursebooking.model.User;

public class StudentWelcomeActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView tvWelcome;
    Button btnCourse;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        student = (Student) getIntent().getSerializableExtra("user");

        String msg = "Welcome '" + student.getUsername() + "'! You are logged in as 'Student'";
        tvWelcome.setText(msg);

        btnCourse = findViewById(R.id.btnCourse);
        btnCourse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnCourse){
            Intent intent = new Intent(this, StudentCourseListActivity.class);
            intent.putExtra("user", student);
            startActivity(intent);
        }
    }

}