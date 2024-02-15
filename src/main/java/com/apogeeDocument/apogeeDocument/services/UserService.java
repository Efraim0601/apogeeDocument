package com.apogeeDocument.apogeeDocument.services;

import ch.qos.logback.core.net.server.Client;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private EmailValidator emailValidator ;

    //gestion de l'inscription
    public void create(User user){

        //let's verify if email is typed correctly
        if (emailValidator.validateEmail(user.getEmail())) {
            System.out.println("L'adresse e-mail est valide !");
            log.info("your mail is correct !!!:");
        } else {
            log.info("your email is wrong !!!!");
        }



        // vérification de la présence d'un email dans la base de donnée
        Optional<User> userOptional = Optional.ofNullable(this.userRepository.findByEmail(user.getEmail()));
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

        //enregistrement de l'utilisateur et vérification de la présence d'un utilisateur dans la base de donnée
         User userInDatabase = this.userRepository.findByEmail(user.getEmail());
                if(userInDatabase ==null){
                    this.userRepository.save(user);
               }

    }




    //récupérer la liste des utilisateurs
    public List<User> search(){

        return (List<User>) this.userRepository.findAll();
    }

    public User getUser(int id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        return optionalUser.orElse(null);

    }
}
