package my.test.azoft.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import my.test.azoft.model.User;
import my.test.azoft.model.Views;
import my.test.azoft.rest.exception.AlreadyExistException;
import my.test.azoft.rest.exception.NotFoundException;
import my.test.azoft.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@PreAuthorize("hasAnyAuthority('Admin', 'Manager')")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(Views.User.class)
    public Page<User> list(
            @RequestParam(value = "filter", required = false) String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        //todo temp get list for admin -> delete
        User admin = userService.findById(1).get();

        return userService.findByUsernameContaining(Optional.ofNullable(filter), pageable, admin);
    }


    @GetMapping("{id}")
    @JsonView(Views.User.class)
    public User getOne(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        return optionalUser.orElseThrow(NotFoundException::new);
    }

    @PostMapping("/create")
    @JsonView(Views.User.class)
    public User create(@RequestBody @Valid User user) {
        Optional<User> optionalUser = userService.createUser(user);
        return optionalUser.orElseThrow(AlreadyExistException::new);
    }


    @PutMapping()
    @JsonView(Views.User.class)
    public User update(@RequestBody @Valid User user) {
        return userService.updateUser(user).orElseThrow(NotFoundException::new);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }
}
