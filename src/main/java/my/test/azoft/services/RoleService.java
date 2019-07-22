package my.test.azoft.services;

import my.test.azoft.model.Role;
import my.test.azoft.repos.RoleRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public List<Role> findAll(Sort sort) {
        return roleRepo.findAll(sort);
    }

    public List<Role> findAllById(Iterable<Integer> iterable) {
        return roleRepo.findAllById(iterable);
    }

    public <S extends Role> List<S> saveAll(Iterable<S> iterable) {
        return roleRepo.saveAll(iterable);
    }

    public void flush() {
        roleRepo.flush();
    }

    public <S extends Role> S saveAndFlush(S s) {
        return roleRepo.saveAndFlush(s);
    }

    public void deleteInBatch(Iterable<Role> iterable) {
        roleRepo.deleteInBatch(iterable);
    }

    public void deleteAllInBatch() {
        roleRepo.deleteAllInBatch();
    }

    public Role getOne(Integer integer) {
        return roleRepo.getOne(integer);
    }

    public <S extends Role> List<S> findAll(Example<S> example) {
        return roleRepo.findAll(example);
    }

    public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
        return roleRepo.findAll(example, sort);
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleRepo.findAll(pageable);
    }

    public <S extends Role> S save(S s) {
        return roleRepo.save(s);
    }

    public Optional<Role> findById(Integer integer) {
        return roleRepo.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return roleRepo.existsById(integer);
    }

    public long count() {
        return roleRepo.count();
    }

    public void deleteById(Integer integer) {
        roleRepo.deleteById(integer);
    }

    public void delete(Role role) {
        roleRepo.delete(role);
    }

    public void deleteAll(Iterable<? extends Role> iterable) {
        roleRepo.deleteAll(iterable);
    }

    public void deleteAll() {
        roleRepo.deleteAll();
    }

    public <S extends Role> Optional<S> findOne(Example<S> example) {
        return roleRepo.findOne(example);
    }

    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
        return roleRepo.findAll(example, pageable);
    }

    public <S extends Role> long count(Example<S> example) {
        return roleRepo.count(example);
    }

    public <S extends Role> boolean exists(Example<S> example) {
        return roleRepo.exists(example);
    }
}
