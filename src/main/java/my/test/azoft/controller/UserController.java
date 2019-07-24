package my.test.azoft.controller;

import my.test.azoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('Admin')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

}
