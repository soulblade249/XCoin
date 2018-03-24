package com.login.form;

public class Login {

	public static boolean authenticate(String username, String password) {
        if (username.equals("user") && password.equals("pass")) {
            return true;
        }
        return false;
    }
}
