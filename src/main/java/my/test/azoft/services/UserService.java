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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        if (findByLogin(user.getLogin()).isPresent()) {
            return false;
        }
        /*Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findById(3).get();
        roles.add(userRole);*/
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //todo fix this
        save(user);
        /*user.setRoles(roles);
        save(user);*/
        return true;

    }

    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
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
        Optional<User> user = userRepo.findByLogin(s);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
}
