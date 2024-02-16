package com.apogeeDocument.apogeeDocument.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator {
    public boolean validateEmail(String email) {
        // Expression régulière pour vérifier le format de l'adresse e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /*//verification de la présence d'un Email dans la base de donnée
    public boolean emailIsPresent(String email){
        if ()
    }*/
}
