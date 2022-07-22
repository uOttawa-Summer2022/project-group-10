package com.app.coursebooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.coursebooking.model.Course;
import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;

public class InstructorWelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvWelcome;
    Button btnCourse;
    Button btnStudents;
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
        btnStudents = findViewById(R.id.btnStudents);
        btnStudents.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnCourse){
            Intent intent = new Intent(this, InstructorCourseListActivity.class);
            intent.putExtra("user", instructor);
            startActivity(intent);
        }

        if(v == btnStudents){
            String msg = "";
            for(Course course : Database.getInstance().getCourses()){
                if(course.getInstructorUsername() != null && course.getInstructorUsername().equals(instructor.getUsername())){
                    msg += course.getCourseCode() + ":\n";
                    for(String stu : course.getStudents()){
                        Student student = Database.getInstance().getStudent(stu);
                        if(student != null){
                            msg += "    " + student.getId() + "-" + student.getName() + "\n";
                        }
                    }
                }
            }

            if(msg.isEmpty()){
                msg = "No students";
            }
            new AlertDialog.Builder(this)
                    .setPositiveButton("OK", null)
                    .setMessage(msg)
                    .show();
        }


    }
}
