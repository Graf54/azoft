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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static final String URL_REDIRECT = "redirect:/users";

    @GetMapping
    public String listUsers(Model model,
                            @RequestParam(value = "filter", required = false) String filter,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                            @AuthenticationPrincipal User user) {
        Page<User> page = userService.findByUsernameContaining(Optional.ofNullable(filter), pageable);
        addPage(model, page);
        return "users";
    }

    @GetMapping("/find")
    public String find(
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer,
            @RequestParam("filter") String filter) {
        redirectAttributes.addAttribute("filter", filter);
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("usr", userService.findById(id).get());
        model.addAttribute("roles", roleService.findAll());
        return "userEdit";
    }

    @PostMapping("/save")
    public String edit(
            Model model,
            @ModelAttribute("Usr") User userForm,
            @AuthenticationPrincipal User user,
            @RequestParam Map<String, String> form) {
        Optional<User> optional = userService.findByUsername(userForm.getUsername());
        if (optional.isPresent() && optional.get().getId() != userForm.getId()) { // имя пользователя уже есть такое
            model.addAttribute("message", "Имя пользователя уже занято");
            model.addAttribute("usr", userService.findById(userForm.getId()).get());
            model.addAttribute("roles", roleService.findAll());
            return "userEdit";
        }
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
            addPage(model, userService.findAll(pageable));
            return "users";
        }
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            model.addAttribute("message", "Пользователь не найден");
            addPage(model, userService.findAll(pageable));
            return "users";
        }

        userService.deleteById(id);
        return "redirect:/users";
    }

    private Model addPage(Model model, Page page) {
        return model.addAttribute("page", page);
    }
}
