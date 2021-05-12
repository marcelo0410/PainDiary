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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView btn_login_register;
    private EditText etEmail, etPassword;
    private Button btn_login;
    private FirebaseAuth mAuth;
    private String userEmail;

    //for test
    private Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmail = "";


        btn_login_register = (Button) findViewById(R.id.login_register);
        btn_login_register.setOnClickListener(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this); // this refers to onClick method

        etEmail = (EditText) findViewById(R.id.login_email);
        etPassword = (EditText) findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();

        // for test
        btn_home = (Button) findViewById(R.id.direct_home);
        btn_home.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register:
                startActivity(new Intent(this, UserRegisteration.class));
                break;
            case R.id.btn_login:
                userLogin();
                break;
                // for test
            case R.id.direct_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
    }

    private void userLogin(){
        userEmail = etEmail.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();

        if (userEmail.isEmpty()){
            etEmail.setError("Please enter your email!");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            etEmail.setError("Please enter correct email!");
            etEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()){
            etPassword.setError("Please enter your password!");
            etPassword.requestFocus();
            return;
        }

        if (userPassword.length() < 6){
            etPassword.setError("Password length should be more than 6!");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // redirect to the Home Screen
                    // Toast.makeText(MainActivity.this, userEmail, Toast.LENGTH_LONG).show();
                    // share login email


                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("userEmail", userEmail);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please check your information!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}