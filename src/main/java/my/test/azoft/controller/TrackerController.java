package my.test.azoft.controller;

import my.test.azoft.model.Calculate;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.services.UserService;
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
@RequestMapping("/tracker")
public class TrackerController {
    @Autowired
    private ExpensesService expensesService;

    @GetMapping({"/tracker"})
    public String index(Model model) {
        Iterable<Expenses> all = expensesService.findAll();
        model.addAttribute("expenses", all);

        Calculate calculate = new Calculate();
        calculate.setStart(new Date());
        calculate.setEnd(new Date());
        double total = expensesService.getSumm(1, new Date(0), new Date()).orElse(new BigDecimal(0.0)).doubleValue();
        calculate.setTotal(total);
        model.addAttribute("calc", calculate);
        return "index";
    }

    @GetMapping({"/edit"})
    public String edit(@RequestParam int id, Model model) {
        List<Expenses> all = expensesService.findAll();
        model.addAttribute("idEdit", id);
        model.addAttribute("expenses", all);
        return "index";
    }

    @PostMapping({"/edit"})
    public String edit(@ModelAttribute("expenses") Expenses expenses,
                       @RequestParam("dateS") String date,
                       @RequestParam("timeS") String time) {
        Date date1 = getDate(date, time);
        expenses.setDate(date1);
        expensesService.save(expenses);
        return "redirect:/index";
    }

    @GetMapping({"/calc"})
    public String calculate() {

        return "redirect:/index";
    }


    @PostMapping({"/add"})
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
        return "redirect:/index";
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
}
