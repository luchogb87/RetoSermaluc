package com.luiscalixto.reto.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {
    private final PasswordConfig passwordConfig;

    @Autowired
    public PasswordValidator(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    //Password sample: Qwerty@123
    public boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        String passwordRegex = passwordConfig.getPasswordRegex();
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
