package com.example.paindiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paindiary.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisteration extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText etFullName, etEmail, etPassword;
    private TextView tvTitle, tvDesc;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registeration);

        // initialize Firebase instance
        mAuth = FirebaseAuth.getInstance();

        // setOnClickListener
        tvTitle = (TextView) findViewById(R.id.reg_title);
        tvTitle.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btn_reg_register);
        btnRegister.setOnClickListener(this);

        // initialize all the ids
        etEmail = (EditText) findViewById(R.id.reg_email);
        etFullName = (EditText) findViewById(R.id.reg_fullname);
        etPassword = (EditText) findViewById(R.id.reg_password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_title:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_reg_register:
                userRegister();
                break;
        }
    }

    public void userRegister(){
        // get all the information from the Edittext
        String userEmail = etEmail.getText().toString().trim();
        String userFullname = etFullName.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();


        // input validation
        if (userEmail.isEmpty()){
            etEmail.setError("Please enter your email!");
            etEmail.requestFocus();
            return;
        }

        // check email address with regular expression(Patterns)
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            etEmail.setError("Please follow the correct email format!");
            etEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            etPassword.setError("Please enter your password!");
            etPassword.requestFocus();
            return;
        }

        if (userPassword.length() < 6){
            etPassword.setError("Password length should be more than 6!");
            etPassword.requestFocus();
            return;
        }

        if (userFullname.isEmpty()){
            etFullName.setError("Please enter your full name!");
            etFullName.requestFocus();
            return;
        }

        // sign up process
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(userEmail, userPassword, userFullname);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserRegisteration.this, "Registration is successful!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(UserRegisteration.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(UserRegisteration.this, "Registration is failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(UserRegisteration.this, "Registration is failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}