package com.apogeeDocument.apogeeDocument.Controllers;

import com.apogeeDocument.apogeeDocument.dto.AuthenticationDTO;
import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.security.JwtService;
import com.apogeeDocument.apogeeDocument.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE)
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwTservice;

    /*
    *
    * Methode pour la création de l'utilisateur
    *
    * */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "user")
    public void create(@RequestBody User user){
        this.userService.create(user);
        log.info("Inscription effectuée !!!!!!");
    }
    /*
     *
     * login part
     *
     * */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "login")
    public Map<String, String> userLogin(@RequestBody AuthenticationDTO authenticationDTO){

        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password())
        );
        if (authenticate.isAuthenticated()){
            return this.jwTservice.get(authenticationDTO.email());
        }
        log.info("reussi!!!");

        return null;
    }

    /*
     *
     * activation
     *
     * */

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> actvaiton){
        this.userService.activation(actvaiton);
        log.info("Inscription effectuée !!!!!!");
    }

    /*
     *
     * get all user from database
     *
     * */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<User> getAlluser(){
        return this.userService.search();
    }

    /*
     *
     *Get user by ID
     *
     * */

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable int id){
        return this.userService.getUser(id);
    }

}
