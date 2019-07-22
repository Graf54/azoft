package my.test.azoft.services;

import my.test.azoft.model.Expenses;
import my.test.azoft.repos.ExpensesRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesService {
    private final ExpensesRepo expensesRepo;

    public ExpensesService(ExpensesRepo expensesRepo) {
        this.expensesRepo = expensesRepo;
    }

    public List<Expenses> findAll() {
        return expensesRepo.findAll();
    }

    public List<Expenses> findAll(Sort sort) {
        return expensesRepo.findAll(sort);
    }

    public List<Expenses> findAllById(Iterable<Integer> iterable) {
        return expensesRepo.findAllById(iterable);
    }

    public <S extends Expenses> List<S> saveAll(Iterable<S> iterable) {
        return expensesRepo.saveAll(iterable);
    }

    public void flush() {
        expensesRepo.flush();
    }

    public <S extends Expenses> S saveAndFlush(S s) {
        return expensesRepo.saveAndFlush(s);
    }

    public void deleteInBatch(Iterable<Expenses> iterable) {
        expensesRepo.deleteInBatch(iterable);
    }

    public void deleteAllInBatch() {
        expensesRepo.deleteAllInBatch();
    }

    public Expenses getOne(Integer integer) {
        return expensesRepo.getOne(integer);
    }

    public <S extends Expenses> List<S> findAll(Example<S> example) {
        return expensesRepo.findAll(example);
    }

    public <S extends Expenses> List<S> findAll(Example<S> example, Sort sort) {
        return expensesRepo.findAll(example, sort);
    }

    public Page<Expenses> findAll(Pageable pageable) {
        return expensesRepo.findAll(pageable);
    }

    public <S extends Expenses> S save(S s) {
        return expensesRepo.save(s);
    }

    public Optional<Expenses> findById(Integer integer) {
        return expensesRepo.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return expensesRepo.existsById(integer);
    }

    public long count() {
        return expensesRepo.count();
    }

    public void deleteById(Integer integer) {
        expensesRepo.deleteById(integer);
    }

    public void delete(Expenses expenses) {
        expensesRepo.delete(expenses);
    }

    public void deleteAll(Iterable<? extends Expenses> iterable) {
        expensesRepo.deleteAll(iterable);
    }

    public void deleteAll() {
        expensesRepo.deleteAll();
    }

    public <S extends Expenses> Optional<S> findOne(Example<S> example) {
        return expensesRepo.findOne(example);
    }

    public <S extends Expenses> Page<S> findAll(Example<S> example, Pageable pageable) {
        return expensesRepo.findAll(example, pageable);
    }

    public <S extends Expenses> long count(Example<S> example) {
        return expensesRepo.count(example);
    }

    public <S extends Expenses> boolean exists(Example<S> example) {
        return expensesRepo.exists(example);
    }

    public BigDecimal getSumm(int userId, Date start, Date end) {
        return expensesRepo.getSumm(userId, start, end);
    }
}
