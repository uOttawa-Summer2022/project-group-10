package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;

public class InstructorWelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvWelcome;
    Button btnCourse;
    Instructor instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        instructor = (Instructor) getIntent().getSerializableExtra("user");

        String msg = "Welcome '" + instructor.getUsername() + "'! You are logged in as 'Instructor'";
        tvWelcome.setText(msg);

        btnCourse = findViewById(R.id.btnCourse);
        btnCourse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnCourse){
            Intent intent = new Intent(this, InstructorCourseListActivity.class);
            intent.putExtra("user", instructor);
            startActivity(intent);
        }
    }
}
