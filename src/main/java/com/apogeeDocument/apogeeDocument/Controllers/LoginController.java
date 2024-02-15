package com.apogeeDocument.apogeeDocument.Controllers;

import com.apogeeDocument.apogeeDocument.entites.User;
import com.apogeeDocument.apogeeDocument.services.LoginService;
import com.apogeeDocument.apogeeDocument.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(path = "login")
public class LoginController {

    private LoginService loginService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void saveUser(@RequestBody User user){
        this.loginService.saveUser(user);
    }

}
