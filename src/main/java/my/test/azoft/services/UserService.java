package my.test.azoft.services;

import my.test.azoft.model.Role;
import my.test.azoft.model.User;
import my.test.azoft.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public boolean createUser(User user) {
        if (findByLogin(user.getUsername()).isPresent()) {
            return false;
        }

        Role userRole = roleService.findById(3).get();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(userRole));
        save(user);
        return true;

    }

    public Optional<User> findByLogin(String login) {
        return userRepo.findByUsername(login);
    }

    public <S extends User> S save(S s) {
        return userRepo.save(s);
    }

    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return userRepo.saveAll(iterable);
    }

    public Optional<User> findById(Integer integer) {
        return userRepo.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return userRepo.existsById(integer);
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public Iterable<User> findAllById(Iterable<Integer> iterable) {
        return userRepo.findAllById(iterable);
    }

    public long count() {
        return userRepo.count();
    }

    public void deleteById(Integer integer) {
        userRepo.deleteById(integer);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    public void deleteAll(Iterable<? extends User> iterable) {
        userRepo.deleteAll(iterable);
    }

    public void deleteAll() {
        userRepo.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(s);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public void updateUser(User userEditable, Map<String, String> form) {
        Optional<User> maybeUser = userRepo.findById(userEditable.getId());
        if (maybeUser.isPresent()) {
            User userFromBd = maybeUser.get();
            if (userEditable.getPassword() != null) {
                userFromBd.setPassword(passwordEncoder.encode(userEditable.getPassword()));
            }
            userFromBd.setUsername(userEditable.getUsername());
            userFromBd.getRoles().clear();

            // из формы берем роли, сравниваем с наличием в бд и добавляем юзеру если есть
            List<Role> roles = roleService.findAll();
            for (String key : form.keySet()) {
                roles.stream()
                        .filter(role -> role.getName().equalsIgnoreCase(key))
                        .findFirst().
                        ifPresent(role -> userFromBd.getRoles().add(role));
            }


            userRepo.save(userFromBd);
        }
    }
}
