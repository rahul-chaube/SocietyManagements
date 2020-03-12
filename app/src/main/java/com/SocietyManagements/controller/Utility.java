package com.SocietyManagements.controller;

import org.apache.commons.validator.routines.EmailValidator;

public class Utility {
    public static boolean isValidEmail(String email)
    {
        return EmailValidator.getInstance().isValid(email);

    }
}
