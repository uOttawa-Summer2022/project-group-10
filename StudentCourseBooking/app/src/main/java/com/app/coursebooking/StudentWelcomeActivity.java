package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.coursebooking.model.Student;
import com.app.coursebooking.model.User;

public class StudentWelcomeActivity extends AppCompatActivity {
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        Student student = (Student) getIntent().getSerializableExtra("user");

        String msg = "Welcome '" + student.getUsername() + "'! You are logged in as 'Student'";
        tvWelcome.setText(msg);
    }
}