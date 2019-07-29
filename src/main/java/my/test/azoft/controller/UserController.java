package my.test.azoft.controller;

import my.test.azoft.model.User;
import my.test.azoft.services.RoleService;
import my.test.azoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('Admin')||hasAuthority('Manager')")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listUsers(Model model,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        addPage(model, pageable);
        return "users";
    }

    @GetMapping("/find")
    public String find(
            @RequestParam("filter") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> page = userService.findByUsernameContaining(filter, pageable);
        model.addAttribute("page", page);
        return "users";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        //todo check present
        model.addAttribute("usr", userService.findById(id).get());
        model.addAttribute("roles", roleService.findAll());
        return "userEdit";
    }

    // TODO: 25.07.2019 check user use
    @PostMapping("/save")
    public String edit(
            @ModelAttribute("Usr") User userForm,
            @AuthenticationPrincipal User user,
            @RequestParam Map<String, String> form) {
        userService.updateUser(userForm, form);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(
            @RequestParam("id") int id,
            @AuthenticationPrincipal User user,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if (user.getId() == id) {
            model.addAttribute("message", "Вы не можете удалить сами себя!");
            addPage(model, pageable);
            return "users";
        }
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            model.addAttribute("message", "Пользователь не найден");
            addPage(model, pageable);
            return "users";
        }

        userService.deleteById(id);
        return "redirect:/users";
    }

    private Model addPage(Model model, Pageable pageable) {
        return model.addAttribute("page", userService.findAll(pageable));
    }
}
