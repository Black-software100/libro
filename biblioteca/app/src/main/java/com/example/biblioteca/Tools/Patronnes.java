package com.example.biblioteca.Tools;

import java.util.regex.Pattern;

public class Patronnes {

    public static final  Pattern Passwordpatter = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$"
    );



}
