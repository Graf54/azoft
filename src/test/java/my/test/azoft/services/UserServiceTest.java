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
        User user = new User();
        user.setLogin("login");
        user.setPassword("test");
        userService.createUser(user);
        Optional<User> login = userService.findByLogin("login");
        Assert.assertEquals(login.get().getLogin(), user.getLogin());
        Assert.assertTrue(login.get().getRoles().stream().anyMatch(role -> role.getId() == 3));

    }

    private void createRole(int id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        roleService.save(role);
    }
}