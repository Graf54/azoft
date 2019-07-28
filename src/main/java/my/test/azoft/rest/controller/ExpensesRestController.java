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

import java.util.Map;
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
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Expenses> page = expensesService.findAllByUserAndFilter(user, pageable, Optional.empty());
        return new ExpensesPageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }

    @GetMapping("{id}")
    @JsonView(Views.UserExpenses.class)
    public Expenses getOne(@PathVariable int id) {
        Optional<Expenses> optional = expensesService.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundException();
        }
    }


    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> expenses) {
        return null;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> expenses) {
        return null;

    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {

    }
}
