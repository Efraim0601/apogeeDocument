package com.apogeeDocument.apogeeDocument.Controllers;

import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody User user){
        this.userService.create(user);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<User> getAlluser(){
        return this.userService.search();
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable int id){
        return this.userService.getUser(id);
    }

}
