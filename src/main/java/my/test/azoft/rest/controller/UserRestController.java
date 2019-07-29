package my.test.azoft.rest.controller;

import my.test.azoft.model.User;
import my.test.azoft.rest.exception.NotFoundException;
import my.test.azoft.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> list() {
        return null;
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException();
        }
    }


    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> expenses) {
        return null;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable int id, @RequestBody Map<String, String> expenses) {

        return null;
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        userService.deleteById(id);
    }
}
