package my.test.azoft.controller;

import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.services.UserService;
import my.test.azoft.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public String expenses(Model model,
                           @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam int id) {
        fillMain(model, pageable, id);
        return "expensesForAdmin";
    }

    private void fillMain(Model model, Pageable pageable, int targetUserId) {
        Optional<User> optionalUser = userService.findById(targetUserId);
        if (optionalUser.isPresent()) {
            Page<Expenses> page = expensesService.findAllByUser(optionalUser.get(), pageable);
            model.addAttribute("page", page);
            model.addAttribute("userId", targetUserId);
            model.addAttribute("userName", optionalUser.get().getUsername());
        }
        //todo else
    }


    @GetMapping("/edit")
    public String edit(@RequestParam int id,
                       @RequestParam int userId,
                       @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        fillMain(model, pageable, userId);
        model.addAttribute("idEdit", id);
        return "expensesForAdmin";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("expenses") Expenses expenses,
                       @RequestParam("dateS") String date,
                       @RequestParam("timeS") String time,
                       @RequestParam("userId") int userId) {
        expensesService.updateFormForm(expenses, DateUtil.getDate(date, time));
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
            @AuthenticationPrincipal User admin,
            @ModelAttribute("expenses") Expenses expenses,
            @RequestParam("dateS") String date,
            @RequestParam("timeS") String time,
            @RequestParam("userId") int userId) {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            expenses.setUser(optionalUser.get());
            Date fullDate = DateUtil.getDate(date, time);
            expenses.setDate(fullDate);
            expensesService.save(expenses);
        }
        return "redirect:/expenses/admin/user?id=" + userId;
    }
}
