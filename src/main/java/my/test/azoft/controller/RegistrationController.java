package my.test.azoft.controller;

import my.test.azoft.model.User;
import my.test.azoft.services.RoleService;
import my.test.azoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String createUser(@RequestParam("password2") String passwordConfirm,
                             @Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean hasError = false;
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Подтверждение пароля не может быть пустым");
            hasError = true;
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Пароли различаются");
            hasError = true;
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || hasError) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.createUser(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует!");
            return "registration";
        }
        return "redirect:/login";
    }

}
