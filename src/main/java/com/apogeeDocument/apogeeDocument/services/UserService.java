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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserService implements UserDetailsService {
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

        //enregistrement de l'utilisateur et vérification de la présence d'un utilisateur dans la base de donnée
         Optional<User> userInDatabase = this.userRepository.findByEmail(user.getEmail());
                if(userInDatabase.isEmpty()){
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

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.getByCode(activation.get("code"));

        if(Instant.now().isAfter(validation.getExpire())){
            throw  new RuntimeException("Your activation key has expired");
        }
        User activeuser = this.userRepository.findById(validation.getUser().getNumero_employe()).orElseThrow(()->new RuntimeException("Unknown user !!!"));
        activeuser.setActive(true);
        validation.setActivation(Instant.now());
        this.userRepository.save(activeuser);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("no user matched !!!"));

    }

    public void changePassword(Map<String, String> args) {
        User user = this.loadUserByUsername(args.get("email"));
        this.validationService.keppValidation(user);
    }

    public void newPassword(Map<String, String> args) {
        User user = this.loadUserByUsername(args.get("email"));
        final Validation validation = validationService.getByCode(args.get("code"));
        if(validation.getUser().getEmail().equals(user.getEmail())){
            String cryptPassword = this.passwordEncoder.encode(args.get("password"));
            user.setPassword(cryptPassword);
        }

    }
}
