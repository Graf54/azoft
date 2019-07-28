package my.test.azoft.controller;

import my.test.azoft.model.Calculate;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.User;
import my.test.azoft.services.ExpensesService;
import my.test.azoft.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    @GetMapping
    public String expenses(Model model,
                           @RequestParam(required = false) String idEdit,
                           @RequestParam(required = false) String filter,
                           @AuthenticationPrincipal User user,
                           @PageableDefault(sort = {"date", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        if (idEdit != null) {
            try {
                int idInt = Integer.parseInt(idEdit);
                model.addAttribute("idEdit", idInt);
            } catch (NumberFormatException ignore) {
            }
        }
        setPage(model, user, pageable, filter);
        setCalculate(model, user, null, null);
        return "expenses";
    }

    private void setPage(Model model, User user, Pageable pageable, String filter) {
        Page<Expenses> page = expensesService.findAllByUserAndFilter(user, pageable, Optional.ofNullable(filter));
        model.addAttribute("page", page);
    }

    private void setCalculate(Model model, User user, Date startDate, Date endDate) {
        Calculate calculate = expensesService.getCalculate(user.getId(), startDate, endDate);
        model.addAttribute("calc", calculate);
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        redirectAttributes.addAttribute("idEdit", id);
        return redirect(redirectAttributes, referer);
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("expenses") Expenses expenses,
                       @RequestParam("dateS") String date,
                       @RequestParam("timeS") String time,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        expensesService.updateFormForm(expenses, DateUtil.getDate(date, time));
        redirectAttributes.addAttribute("idEdit", 0); // очишаем поле редактирования
        return redirect(redirectAttributes, referer);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id,
                         @AuthenticationPrincipal User user,
                         RedirectAttributes redirectAttributes,
                         @RequestHeader(required = false) String referer) {
        expensesService.deleteByIdAndUser(id, user);
        return redirect(redirectAttributes, referer);
    }

    @GetMapping("/calc")
    public String calculate(Model model,
                            @RequestParam("dateS") String start,
                            @RequestParam("dateE") String end,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer,
                            @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable,
                            @AuthenticationPrincipal User user) {
        setPage(model, user, pageable, null);
        Date startDate = DateUtil.getDate(start);
        Date endDate = DateUtil.getDate(end);
        setCalculate(model, user, startDate, endDate);
        return redirect(redirectAttributes, referer);
    }


    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @ModelAttribute("expenses") Expenses expenses,
            @RequestParam("dateS") String date,
            @RequestParam("timeS") String time,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer) {
        Date date1 = DateUtil.getDate(date, time);
        expenses.setDate(date1);
        expenses.setUser(user);
        expensesService.save(expenses);
        return redirect(redirectAttributes, referer);
    }


    private String redirect(RedirectAttributes redirectAttributes, String referer) {
        if (referer == null || referer.isEmpty()) {
            return "redirect:/expenses";
        }
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .forEach((key, value) -> {
                    if (!(key.equals("idEdit") && redirectAttributes.containsAttribute("idEdit"))) { // если уже содержит, то добавлять не нужно
                        redirectAttributes.addAttribute(key, value);
                    }
                });

        return "redirect:" + components.getPath();
    }

}
