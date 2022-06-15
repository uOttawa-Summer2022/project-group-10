package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.coursebooking.model.Admin;
import com.app.coursebooking.model.Student;

public class AdminWelcomeActivity extends AppCompatActivity {
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        tvWelcome = findViewById(R.id.tvWelcome);
        Admin admin = (Admin) getIntent().getSerializableExtra("user");

        String msg = "Welcome '" + admin.getUsername() + "'! You are logged in as 'Admin'";
        tvWelcome.setText(msg);
    }
}