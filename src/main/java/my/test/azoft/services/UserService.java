package my.test.azoft.services;

import my.test.azoft.model.Role;
import my.test.azoft.model.User;
import my.test.azoft.repos.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ExpensesService expensesService;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleService roleService, ExpensesService expensesService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.expensesService = expensesService;
    }

    public Optional<User> createUser(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            return Optional.empty();
        }

        Role userRole = roleService.findById(3).get();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(userRole));
        return Optional.of(save(user));

    }

    public Page<User> findByUsernameContaining(Optional<String> username, Pageable pageable, User editor) {
        if (!editor.isAdmin()) {
            // admin id = 1
            if (username.isPresent()) {
                return userRepo.findAllExceptRoleIdAndUsernameLike(1, username.get(), pageable);
            } else {
                return userRepo.findAllExceptRoleId(1, pageable);
            }
        }
        if (username.isPresent()) {
            return userRepo.findByUsernameContaining(username.get(), pageable);
        } else {
            return userRepo.findAll(pageable);
        }
    }

    public Optional<User> findByUsername(String login) {
        return userRepo.findByUsername(login);
    }

    public <S extends User> S save(S s) {
        return userRepo.save(s);
    }


    public Optional<User> findById(Integer integer) {
        return userRepo.findById(integer);
    }


    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
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


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(s);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }


    public void updateUser(User userEditable, Map<String, String> form) {
        Optional<User> optionalUser = userRepo.findById(userEditable.getId());
        if (optionalUser.isPresent()) {
            User userFromBd = optionalUser.get();
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

    public Optional<User> updateUser(User userEditable) {
        Optional<User> optionalUser = userRepo.findById(userEditable.getId());
        if (optionalUser.isPresent()) {
            User userFromBd = optionalUser.get();
            if (userEditable.getPassword() != null) {
                userFromBd.setPassword(passwordEncoder.encode(userEditable.getPassword()));
            }
            userFromBd.setUsername(userEditable.getUsername());
            userRepo.save(userFromBd);
        }
        return optionalUser;
    }
}
