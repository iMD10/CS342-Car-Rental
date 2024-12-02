package org.example.common;

public class Validation {
    public boolean checkEmail(String email) {
        return email.contains("@");
    }
    public boolean checkPassword(String password) {
        return password.length() >= 6;
    }
    public boolean checkName(String name) {
        return name.matches("^[a-zA-Z0-9_-]+$");
    }
    public boolean checkPhone(String phone){
        return phone.matches("^[0-9]{10}");
    }
}
