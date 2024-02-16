package com.apogeeDocument.apogeeDocument.services;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.apogeeDocument.apogeeDocument.entites.Role;
import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.entites.Validation;
import com.apogeeDocument.apogeeDocument.enumerable.RoleType;
import com.apogeeDocument.apogeeDocument.repositories.UserRepository;
import com.apogeeDocument.apogeeDocument.security.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ValidationService validationService;
    private EmailValidator emailValidator ;

    //gestion de l'inscription
    public void create(User user){
        this.emailValidator = new EmailValidator();
        passwordEncoder = new BCryptPasswordEncoder();

        //let's verify if email is typed correctly
        if (this.emailValidator.validateEmail(user.getEmail())) {
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
                    user = this.userRepository.save(user);
                    this.validationService.keppValidation(user);
               }

    }




    /**
     *
     * Get all users
     *
     *
     */
    public List<User> search(){

        return (List<User>) this.userRepository.findAll();
    }

    /**
     *
     * get all user by id
     *
     *
     */

    public User getUser(int id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        return optionalUser.orElse(null);

    }

    /**
     *
     * Gestion du code d'activation
     *
     *
     */

    public void activation(Map<String, String> actvaiton) {
        Validation validation = this.validationService.getByCode(actvaiton.get("code"));

        if(Instant.now().isAfter(validation.getExpire())){
            throw  new RuntimeException("Your activation key has expired");
        }
        User activeuser = this.userRepository.findById(validation.getUser().getNumero_employe()).orElseThrow(()->new RuntimeException("Unknown user !!!"));
        activeuser.setActive(true);
        this.userRepository.save(activeuser);
    }
}
