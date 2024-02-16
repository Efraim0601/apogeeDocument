package com.apogeeDocument.apogeeDocument.services;

import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.entites.Validation;
import com.apogeeDocument.apogeeDocument.repositories.ValidationRepository;
import com.apogeeDocument.apogeeDocument.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@AllArgsConstructor
public class ValidationService {
    private NotificationService notificationService;
    private ValidationRepository validationRepository;

    public void keppValidation(User user){
        Validation validation = new Validation();
        validation.setUser(user);

        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpire(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);


        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation getByCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(()->new RuntimeException("invalide activation key !!!"));
    }
}
