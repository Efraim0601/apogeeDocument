package com.apogeeDocument.apogeeDocument.Controllers;

import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Methode pour le création de l'utilisateur
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "user")
    public void create(@RequestBody User user){
        this.userService.create(user);
        log.info("Inscription effectuée !!!!!!");
    }


    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> actvaiton){
        this.userService.activation(actvaiton);
        log.info("Inscription effectuée !!!!!!");
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<User> getAlluser(){
        return this.userService.search();
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable int id){
        return this.userService.getUser(id);
    }

}
