package com.app.coursebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.coursebooking.model.Database;
import com.app.coursebooking.model.Instructor;
import com.app.coursebooking.model.Student;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername;
    EditText etPassword;
    EditText etId;
    EditText etName;
    EditText etEmail;
    EditText etPhone;
    Spinner spType;

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        spType = findViewById(R.id.spType);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

        if (Database.getInstance().getUser(username) != null) {
            Toast.makeText(this, "username already exists.", Toast.LENGTH_LONG).show();
            return;
        }

        if (username.isEmpty()) {
            Toast.makeText(this, "username is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (username.equals("admin")) {
            Toast.makeText(this, "can not register as an admin", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "password is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (id.isEmpty()) {
            Toast.makeText(this, "id is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (name.isEmpty()) {
            Toast.makeText(this, "name is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "email is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "phone is empty", Toast.LENGTH_LONG).show();
            return;
        }

        int type = spType.getSelectedItemPosition();
        if (type == 0) {
            Database.getInstance().addInstructor(new Instructor(username, password, id, name, email, phone));
            Database.getInstance().save(this);
        } else {
            Database.getInstance().addStudent(new Student(username, password, id, name, email, phone));
            Database.getInstance().save(this);
        }

        finish();
    }
}