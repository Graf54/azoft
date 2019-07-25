package my.test.azoft.controller;

import my.test.azoft.model.Calculate;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    @GetMapping
    public String tracker(Model model,
                          @AuthenticationPrincipal User user) {
        Iterable<Expenses> all = expensesService.findAllByUserOrderByDate(user);
        model.addAttribute("expenses", all);
        setCalculate(model, user, new Date(0), new Date());
        return "expenses";
    }

    private void setCalculate(Model model, User user, Date startDate, Date endDate) {
        Calculate calculate = new Calculate();
        calculate.setStart(new Date());
        calculate.setEnd(new Date());
        BigDecimal defoult = new BigDecimal(0.0);
        double total = expensesService.getSumm(user.getId(), startDate, endDate).orElse(defoult).doubleValue();
        double average = expensesService.getAverage(user.getId(), startDate, endDate).orElse(defoult).doubleValue();
        calculate.setTotal(total);
        calculate.setAverage(average);
        model.addAttribute("calc", calculate);
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id,
                       Model model,
                       @AuthenticationPrincipal User user) {
        List<Expenses> all = expensesService.findAllByUserOrderByDate(user);
        model.addAttribute("idEdit", id);
        model.addAttribute("expenses", all);
        setCalculate(model, user, new Date(0), new Date());
        return "expenses";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("expenses") Expenses expenses,
                       @RequestParam("dateS") String date,
                       @RequestParam("timeS") String time,
                       @AuthenticationPrincipal User user) {
        Date date1 = getDate(date, time);
        expenses.setDate(date1);
        expenses.setUser(user);
        expensesService.save(expenses);
        return "redirect:/expenses";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id,
                         @AuthenticationPrincipal User user) {
        expensesService.deleteById(id, user);
        return "redirect:/expenses";
    }

    @GetMapping("/calc")
    public String calculate(Model model,
                            @RequestParam("dateS") String start,
                            @RequestParam("dateE") String end,
                            @AuthenticationPrincipal User user
    ) {
        Iterable<Expenses> all = expensesService.findAllByUserOrderByDate(user);
        model.addAttribute("expenses", all);
        Date startDate = getDate(start);
        Date endDate = getDate(end);
        setCalculate(model, user, startDate, endDate);
        return "expenses";
    }


    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @ModelAttribute("expenses") Expenses expenses,
            @RequestParam("dateS") String date,
            @RequestParam("timeS") String time
    ) {
        Date date1 = getDate(date, time);
        expenses.setDate(date1);
        expenses.setUser(user);
        expensesService.save(expenses);
        return "redirect:/expenses";
    }

    private Date getDate(String date, String time) {
        Date date1;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + time);
        } catch (ParseException e) {
            date1 = new Date();
        }
        return date1;
    }

    private Date getDate(String datetimeLocal) {
        Date date1;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(datetimeLocal);
        } catch (ParseException e) {
            date1 = new Date();
        }
        return date1;
    }
}
