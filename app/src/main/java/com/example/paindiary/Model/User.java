package com.example.paindiary.Model;

public class User {
    public String email;
    public String password;
    public String fullname;

    public User() {
    }

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }
}
