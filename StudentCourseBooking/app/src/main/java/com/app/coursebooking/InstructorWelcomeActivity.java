package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;

public class InstructorWelcomeActivity extends AppCompatActivity {

    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        Instructor instructor = (Instructor) getIntent().getSerializableExtra("user");

        String msg = "Welcome '" + instructor.getUsername() + "'! You are logged in as 'Instructor'";
        tvWelcome.setText(msg);
    }
}
