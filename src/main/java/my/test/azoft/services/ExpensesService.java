package my.test.azoft.services;

import my.test.azoft.model.Calculate;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.repos.ExpensesRepo;
import my.test.azoft.util.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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


    public Page<Expenses> findAllByUserAndFilter(User user, Pageable pageable, Optional<String> dateFilter) {
        if (dateFilter.isPresent()) {
            Date date = DateUtil.getDateByDay(dateFilter.get());
            return expensesRepo.findAllByUserAndDate(user.getId(), date, pageable);
        }
        return expensesRepo.findAllByUser(user, pageable);
    }


    public Expenses getOne(Integer integer) {
        return expensesRepo.getOne(integer);
    }

    public <S extends Expenses> S save(S s) {
        return expensesRepo.save(s);
    }

    public Optional<Expenses> findById(Integer integer) {
        return expensesRepo.findById(integer);
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

    public BigDecimal getSumm(int userId, Date start, Date end) {
        return expensesRepo.getSumm(userId, start, end).orElse(new BigDecimal(0.0));
    }
}
