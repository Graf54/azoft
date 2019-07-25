package my.test.azoft.controller;

import my.test.azoft.model.Calculate;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    @GetMapping
    public String expenses(Model model,
                           @AuthenticationPrincipal User user) {
        Iterable<Expenses> all = expensesService.findAllByUserOrderByDate(user);
        model.addAttribute("expenses", all);
        Date first = expensesService.getFirstDate(user.getId()).orElse(new Date());
        Date last = expensesService.getLastDate(user.getId()).orElse(new Date());
        setCalculate(model, user, first, last);
        return "expenses";
    }

    private void setCalculate(Model model, User user, Date startDate, Date endDate) {
        Calculate calculate = new Calculate();
        calculate.setStart(startDate);
        calculate.setEnd(endDate);
        double total = expensesService.getSumm(user.getId(), startDate, endDate).doubleValue();
        double average = expensesService.getAverage(user.getId(), startDate, endDate).doubleValue();
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
                       @RequestParam("timeS") String time) {
        expensesService.saveFormForm(expenses, DateUtil.getDate(date, time));
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
                            @AuthenticationPrincipal User user) {
        Iterable<Expenses> all = expensesService.findAllByUserOrderByDate(user);
        model.addAttribute("expenses", all);
        Date startDate = DateUtil.getDate(start);
        Date endDate = DateUtil.getDate(end);
        setCalculate(model, user, startDate, endDate);
        return "expenses";
    }


    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @ModelAttribute("expenses") Expenses expenses,
            @RequestParam("dateS") String date,
            @RequestParam("timeS") String time) {
        Date date1 = DateUtil.getDate(date, time);
        expenses.setDate(date1);
        expenses.setUser(user);
        expensesService.save(expenses);
        return "redirect:/expenses";
    }


}
