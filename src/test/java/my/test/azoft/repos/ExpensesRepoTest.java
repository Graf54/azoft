package my.test.azoft.repos;

import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
public class ExpensesRepoTest {
    @Autowired
    ExpensesRepo expensesRepo;
    @Autowired
    UserRepo userRepo;

    @Test
    public void getSumm() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        User saveUser = userRepo.save(user);
        Expenses expenses = createExpenses(saveUser, 12.2);
        expensesRepo.save(expenses);
        BigDecimal summ = expensesRepo.getSumm(saveUser.getId(), new Date(0), new Date()).get();

        Assert.assertEquals(12.2, summ.doubleValue(), 0.0);

        expensesRepo.save(createExpenses(saveUser, 12.2));
        summ = expensesRepo.getSumm(saveUser.getId(), new Date(0), new Date()).get();

        Assert.assertEquals(24.4, summ.doubleValue(), 0.0);
    }

    private Expenses createExpenses(User save, double value) {
        Expenses expenses = new Expenses();
        expenses.setDate(new Date());
        expenses.setDescription("");
        expenses.setValue(new BigDecimal(value));
        expenses.setUser(save);
        return expenses;
    }

    @Test
    public void findById() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        User save = userRepo.save(user);
        Expenses expenses = createExpenses(save, 12.2);
        expensesRepo.save(expenses);
        Expenses one = expensesRepo.getOne(expenses.getId());

        Assert.assertEquals(12.2, one.getValue().doubleValue(), 0.0);
    }
}