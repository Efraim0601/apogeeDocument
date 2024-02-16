package com.apogeeDocument.apogeeDocument.services;

import com.apogeeDocument.apogeeDocument.entites.Validation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
    JavaMailSender javaMailSender;
    public void envoyer(Validation validation){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("takouaefraim@gmail.com");
        mailMessage.setTo(validation.getUser().getEmail());
        mailMessage.setSubject("Votre code d'activation");

        String text = String.format("Bonjour %s, <br/>" +
                "votre code d'activation est : %s; A bientôt",
                validation.getUser().getName(),
                validation.getCode());
        mailMessage.setText((text));

        javaMailSender.send(mailMessage);
    }
}
