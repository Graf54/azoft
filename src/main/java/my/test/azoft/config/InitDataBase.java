package my.test.azoft.config;

import my.test.azoft.model.Role;
import my.test.azoft.model.User;
import my.test.azoft.repos.RoleRepo;
import my.test.azoft.repos.UserRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitDataBase {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public InitDataBase(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        addRoles();
        addAdmin();

    }

    private void addRoles() {
        addRole(1, "Admin");
        addRole(2, "Manager");
        addRole(3, "User");

    }

    private void addRole(int id, String name) {
        if (!roleRepo.findById(id).isPresent()) {
            Role role = new Role();
            role.setId(id);
            role.setName(name);
            roleRepo.save(role);
        }
    }

    private void addAdmin() {
        String username = "Admin";
        if (!userRepo.findByUsername(username).isPresent()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(username));
            user.setRoles(roleRepo.findAll());
            userRepo.save(user);
        }
    }
}
