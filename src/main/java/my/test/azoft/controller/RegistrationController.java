package my.test.azoft.controller;

import my.test.azoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
}
