package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;
import com.app.coursebooking.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername;
    EditText etPassword;

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "username is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "password is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (Database.getInstance().getUser(username) == null) {
            Toast.makeText(this, "username doesn't exist.", Toast.LENGTH_LONG).show();
            return;
        }

        User user = Database.getInstance().login(username, password);
        if(user == null){
            Toast.makeText(this, "password is incorrect", Toast.LENGTH_LONG).show();
            return;
        }

        if(user instanceof Instructor){
            Intent intent = new Intent(this, InstructorWelcomeActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }else if(user instanceof Student){
            Intent intent = new Intent(this, StudentWelcomeActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, AdminWelcomeActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}