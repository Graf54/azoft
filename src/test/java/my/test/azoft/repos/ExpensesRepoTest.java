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

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class ExpensesRepoTest {
    @Autowired
    ExpensesRepo expensesRepo;
    @Autowired
    UserRepo userRepo;

    @Test
    public void getSumm() {
        User user = new User();
        user.setLogin("");
        user.setPassword("");
        User save = userRepo.save(user);
        Expenses expenses = new Expenses();
        expenses.setDate(new Date());
        expenses.setDescription("");
        expenses.setValue(new BigDecimal(12.2));
        expenses.setUser(save);
        expensesRepo.save(expenses);
        BigDecimal summ = expensesRepo.getSumm(save.getId(), new Date(0), new Date());
        Assert.assertEquals(12.2, summ.doubleValue(), 0.0);
    }
}