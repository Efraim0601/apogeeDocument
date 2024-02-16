package com.apogeeDocument.apogeeDocument.services;

import com.apogeeDocument.apogeeDocument.entites.Role;
import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.enumerable.RoleType;
import com.apogeeDocument.apogeeDocument.repositories.UserRepository;
import com.apogeeDocument.apogeeDocument.security.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class LoginService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private EmailValidator emailValidator ;
    public void saveUser(User user) {

        //let's verify if email is typed correctly
        if (emailValidator.validateEmail(user.getEmail())) {
            System.out.println("L'adresse e-mail est valide !");
            log.info("your mail is correct !!!:");
        } else {
            log.info("your email is wrong !!!!");
        }



        // vérification de la présence d'un email dans la base de donnée
        Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new RuntimeException("Email already used!!! ");
        }

        //encodage du mot de passe avant stockage
        String pwdCrypt = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(pwdCrypt);

        //traitement sur les rôles
        Role role = new Role();
        role.setWording(RoleType.EMPLOYEE);
        user.setWording(role);

        //enregistrement de l'utilisateur
        this.userRepository.save(user);
    }
}
