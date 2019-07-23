package my.test.azoft.services;

import my.test.azoft.model.Role;
import my.test.azoft.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Test
    public void createUser() {
        createRole(1, "Admin");
        createRole(2, "Manager");
        createRole(3, "User");

        Assert.assertEquals(3, roleService.count());

        String login = "username";
        User user = getUser(login, "pass");
        userService.createUser(user);
        Optional<User> foundUser = userService.findByLogin(login);
        Assert.assertEquals(foundUser.get().getUsername(), user.getUsername());
        Assert.assertTrue(foundUser.get().getRoles().stream().anyMatch(role -> role.getId() == 3));

        login = "user2";
        user = getUser("user2", "pass");
        userService.createUser(user);

        foundUser = userService.findByLogin(login);
        Assert.assertEquals(foundUser.get().getUsername(), user.getUsername());
        Assert.assertTrue(foundUser.get().getRoles().stream().anyMatch(role -> role.getId() == 3));
        Assert.assertEquals(userService.count(), 2);
    }

    private User getUser(String name, String pass) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);
        return user;
    }

    private void createRole(int id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        roleService.save(role);
    }
}