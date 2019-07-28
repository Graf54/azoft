package my.test.azoft.services;

import my.test.azoft.model.Calculate;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.repos.ExpensesRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public BigDecimal getAverage(int userId, Date start, Date end) {
        return expensesRepo.getAverage(userId, start, end).orElse(new BigDecimal(0.0));
    }

    public Optional<Date> getFirstDate(int userId) {
        return expensesRepo.getFirstDate(userId);
    }

    public Optional<Date> getLastDate(int userId) {
        return expensesRepo.getLastDate(userId);
    }

    public Optional<Expenses> findByIdAndUser(int id, User user) {
        return expensesRepo.findByIdAndUser(id, user);
    }

    public void deleteByIdAndUser(Integer integer) {
        expensesRepo.deleteById(integer);
    }

    public Page<Expenses> findAllByUser(User user, Pageable pageable) {
        return expensesRepo.findAllByUser(user, pageable);
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

    public void deleteByIdAndUser(int id, User user) {
        if (expensesRepo.findByIdAndUser(id, user).isPresent()) {
            expensesRepo.deleteById(id);
        }
    }

    public boolean updateFormForm(Expenses expFromForm, Date date) {
        Optional<Expenses> expensesOptional = expensesRepo.findById(expFromForm.getId());
        if (expensesOptional.isPresent()) {
            Expenses expensesDB = expensesOptional.get();
            expensesDB.setDate(date);
            expensesDB.setComment(expFromForm.getComment());
            expensesDB.setDescription(expFromForm.getDescription());
            expensesDB.setValue(expFromForm.getValue());
            expensesRepo.save(expensesDB);
            return true;
        }
        return false;
    }

    public Calculate getCalculate(int userId, Date first, Date last) {
        Calculate calculate = new Calculate();
        if (first == null) {
            first = getFirstDate(userId).orElse(new Date());
        }
        if (last == null) {
            last = getLastDate(userId).orElse(new Date());
        }
        if (first.after(last)) { // меняем местами если пользователь поставил даты не верно
            Date temp = first;
            first = last;
            last = temp;

        }
        calculate.setStart(first);
        calculate.setEnd(last);
        double total = getSumm(userId, first, last).doubleValue();
        double average = getAverage(userId, first, last).doubleValue();
        calculate.setTotal(total);
        calculate.setAverage(average);
        return calculate;
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
        return expensesRepo.getSumm(userId, start, end).orElse(new BigDecimal(0.0));
    }
}
