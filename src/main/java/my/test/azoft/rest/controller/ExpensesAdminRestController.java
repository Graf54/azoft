package my.test.azoft.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.model.Views;
import my.test.azoft.rest.exception.NotFoundException;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('Admin')")
public class ExpensesAdminRestController {
    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @JsonView(Views.UserExpenses.class)
    public Page<Expenses> expenses(@PageableDefault(sort = {"date", "id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                   @RequestParam("userId") int userId,
                                   @RequestParam(value = "filter", required = false) String filter) {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        return expensesService.findAllByUserAndFilter(user, pageable, Optional.ofNullable(filter));
    }


    @PutMapping("/edit")
    @JsonView(Views.UserExpenses.class)
    public Expenses edit(@RequestBody Expenses expenses,
                         @RequestBody int userId) {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        expensesService.findByIdAndUser(expenses.getId(), user).orElseThrow(NotFoundException::new);
        return expensesService.updateExpensesFromForm(expenses).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") int id) {
        Expenses expenses = expensesService.findById(id).orElseThrow(NotFoundException::new);
        expensesService.delete(expenses);
    }


    @PostMapping("/add")
    @JsonView(Views.UserExpenses.class)
    public Expenses add(@RequestBody Expenses expenses,
                        @RequestBody int userId) {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        expenses.setUser(user);
        return expensesService.save(expenses);
    }
}
