package my.test.azoft.services;

import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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
        User tempUser = createTempUser();
        addStandardExpenses(tempUser, 12.2);  // создаем запись с текущем момента

        BigDecimal avr = expensesService.getAverage(tempUser.getId(), new Date(0), new Date());

        Assert.assertEquals(12.2, avr.doubleValue(), 0.0);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        addStandardExpenses(tempUser, 12.2, calendar.getTime()); // создаем запись завтрашнего дня
        avr = expensesService.getAverage(tempUser.getId(), new Date(0), new Date());

        Assert.assertEquals(12.2, avr.doubleValue(), 0.0);
        Assert.assertNotEquals(12.4, avr.doubleValue(), 0.0);
    }


    @Test
    public void findById() {
        User save = createTempUser();
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
        double testValue = 12.2;
        User saveUser = createTempUser();
        addStandardExpenses(saveUser, testValue);
        BigDecimal summ = expensesService.getSumm(saveUser.getId(), new Date(0), new Date());

        Assert.assertEquals(testValue, summ.doubleValue(), 0.0);
        addStandardExpenses(saveUser, testValue);
        summ = expensesService.getSumm(saveUser.getId(), new Date(0), new Date());

        Assert.assertEquals(24.4, summ.doubleValue(), 0.0);
    }

    @Test
    public void saveFormForm() {
        Expenses expenses = addStandardExpenses(createTempUser(), 12.2);
        Expenses expensesFromForm = new Expenses();
        expensesFromForm.setId(expenses.getId());
        expensesFromForm.setUser(null);
        String comment = "new comment";
        expensesFromForm.setComment(comment);
        expensesFromForm.setValue(new BigDecimal(24.4));
        String desc = "new desc";
        expensesFromForm.setDescription(desc);
        Date date = DateUtil.getDate("2019-04-01'T'15:00");
        expensesFromForm.setDate(date);
        expensesService.updateExpensesFromForm(expensesFromForm);

        Expenses updatedExp = expensesService.getOne(expenses.getId());

        Assert.assertEquals(updatedExp.getComment(), comment);
        Assert.assertEquals(updatedExp.getDescription(), desc);
        Assert.assertEquals(updatedExp.getDate(), date);
        Assert.assertEquals(updatedExp.getValue(), expensesFromForm.getValue());
        Assert.assertNotEquals(updatedExp.getUser(), expensesFromForm.getUser()); // юзера не обновляем 


    }


    @Test
    public void findAllByUserAndFilter() {
        User tempUser = createTempUser();
        String day = "2019-09-09";
        Pageable firts = PageRequest.of(1, 10);
        Optional optional = Optional.ofNullable(day);
        addStandardExpenses(tempUser, 20, DateUtil.getDateByDay(day));
        Page page = expensesService.findAllByUserAndFilter(tempUser, firts, optional);
        Assert.assertEquals(1, page.getTotalPages());
        Assert.assertEquals(1, page.getTotalElements());
    }

    //    util method


    private User createTempUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("pass");
        return userService.save(user);
    }

    private Expenses createExpenses(User save, double value, Date date) {
        Expenses expenses = new Expenses();
        expenses.setDate(date);
        expenses.setDescription("");
        expenses.setValue(new BigDecimal(value));
        expenses.setUser(save);
        return expenses;
    }

    private Expenses addStandardExpenses(User tempUser, double v) {
        return addStandardExpenses(tempUser, v, new Date());
    }

    private Expenses addStandardExpenses(User saveUser, double value, Date date) {
        Expenses expenses = createExpenses(saveUser, value, date);
        return expensesService.save(expenses);
    }

    private Expenses createExpenses(User save, double v) {
        return createExpenses(save, v, new Date());
    }


}