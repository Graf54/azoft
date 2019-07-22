package my.test.azoft.services;

import my.test.azoft.model.User;
import my.test.azoft.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

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

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
