package org.example.common;

import org.apache.commons.validator.routines.EmailValidator;

public class Validation {

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    public boolean checkEmail(String email) {
        return email != null && emailValidator.isValid(email);
    }

    public boolean checkPassword(String password) {
        return password.length() >= 6 && password.matches("^[a-zA-Z0-9_-]+$");
    }

    public boolean checkName(String name) {
        return name.matches("^[a-zA-Z]+$");
    }

    public boolean checkPhone(String phone) {
        return phone.matches("^[0-9]{10}$");
    }
}
