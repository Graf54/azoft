package my.test.azoft.controller;

import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.services.UserService;
import my.test.azoft.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/expenses/admin")
@PreAuthorize("hasAuthority('Admin')")
public class ExpensesAdminController {
    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String expenses(Model model, @RequestParam int id) {
        fillMain(model, id);
        return "expensesForAdmin";
    }

    private void fillMain(Model model, int targetUserId) {
        Optional<User> optionalUser = userService.findById(targetUserId);
        Iterable<Expenses> all = expensesService.findAllByUserOrderByDate(optionalUser.get());
        model.addAttribute("expenses", all);
        model.addAttribute("userId", targetUserId);
        model.addAttribute("userName", optionalUser.get().getUsername());

    }


    @GetMapping("/edit")
    public String edit(@RequestParam int id,
                       @RequestParam int userId,
                       Model model) {
        fillMain(model, userId);
        model.addAttribute("idEdit", id);
        return "expensesForAdmin";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("expenses") Expenses expenses,
                       @RequestParam("dateS") String date,
                       @RequestParam("timeS") String time,
                       @RequestParam("userId") int userId) {
        expensesService.saveFormForm(expenses, DateUtil.getDate(date, time));
        return "redirect:/expenses/admin/user?id=" + userId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id,
                         @RequestParam("userId") User userId) {
        expensesService.deleteByIdAndUser(id, userId);
        return "redirect:/expenses/admin/user?id=" + userId.getId();
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
