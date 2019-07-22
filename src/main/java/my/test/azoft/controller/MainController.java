package my.test.azoft.controller;

import my.test.azoft.model.Expenses;
import my.test.azoft.services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ExpensesService expensesService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        Iterable<Expenses> all = expensesService.findAll();
        model.addAttribute("expenses", all);
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
    public String calculate(@ModelAttribute("expenses") Expenses expenses,
                            @RequestParam("dateS") String date,
                            @RequestParam("timeS") String time) {
        Date date1 = getDate(date, time);
        expenses.setDate(date1);
        expensesService.save(expenses);
        return "redirect:/index";
    }


    @PostMapping({"/add"})
    public String add(
            @ModelAttribute("expenses") Expenses expenses,
            @RequestParam("dateS") String date,
            @RequestParam("timeS") String time
    ) {
        Date date1 = getDate(date, time);
        expenses.setDate(date1);
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
