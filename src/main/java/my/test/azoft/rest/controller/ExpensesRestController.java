package my.test.azoft.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.model.Views;
import my.test.azoft.model.dto.ExpensesPageDto;
import my.test.azoft.rest.exception.NotFoundException;
import my.test.azoft.services.ExpensesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRestController {

    private final ExpensesService expensesService;

    public ExpensesRestController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }


    @GetMapping
    @JsonView(Views.UserExpenses.class)
    public ExpensesPageDto list(@AuthenticationPrincipal User user,
                                @RequestParam(value = "filterDay", required = false) String filter,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Expenses> page = expensesService.findAllByUserAndFilter(user, pageable, Optional.ofNullable(filter));
        return new ExpensesPageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }

    @GetMapping("{id}")
    @JsonView(Views.UserExpenses.class)
    public Expenses getOne(@PathVariable int id,
                           @AuthenticationPrincipal User user) {
        return expensesService.findByIdAndUser(id, user).orElseThrow(NotFoundException::new);
    }


    @PostMapping
    @JsonView(Views.UserExpenses.class)
    public Expenses create(@RequestBody Expenses expenses,
                           @AuthenticationPrincipal User user) {
        expenses.setUser(user);
        return expensesService.save(expenses);
    }

    @PutMapping("{id}")
    @JsonView(Views.UserExpenses.class)
    public Expenses update(@PathVariable("id") Expenses expenses,
                           @AuthenticationPrincipal User user) {
        expensesService.findByIdAndUser(expenses.getId(), user).orElseThrow(NotFoundException::new);
        return expensesService.updateExpensesFromForm(expenses).orElseThrow(NotFoundException::new);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        Optional<Expenses> optionalExpenses = expensesService.findById(id);
        if (optionalExpenses.isPresent()) {
            expensesService.delete(optionalExpenses.get());
        } else {
            throw new NotFoundException();
        }
    }
}
