package my.test.azoft.services;

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
public class ExpensesServiceTest {
    @Autowired
    ExpensesService expensesService;

    @Autowired
    UserService userService;

    @Test
    public void getAverage() {
    }

    @Test
    public void findById(){
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        User save = userService.save(user);
        Expenses expenses = createExpenses(save, 12.2);
        expensesService.save(expenses);
        Expenses one = expensesService.getOne(expenses.getId());

        Assert.assertEquals(12.2, one.getValue().doubleValue(), 0.0);
    }

    @Test
    public void findByIdAndUser() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findAll1() {
    }

    @Test
    public void save() {
    }

    @Test
    public void getSumm() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        User saveUser = userService.save(user);
        Expenses expenses = createExpenses(saveUser, 12.2);
        expensesService.save(expenses);
        BigDecimal summ = expensesService.getSumm(saveUser.getId(), new Date(0), new Date()).get();

        Assert.assertEquals(12.2, summ.doubleValue(), 0.0);

        expensesService.save(createExpenses(saveUser, 12.2));
        summ = expensesService.getSumm(saveUser.getId(), new Date(0), new Date()).get();

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
}