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
    private static final String URL_REDIRECT = "redirect:/expenses";

    @GetMapping
    public String expenses(Model model,
                           @RequestParam(required = false) String idEdit,
                           @RequestParam(value = "calcStart", required = false) String start,
                           @RequestParam(value = "calcEnd", required = false) String end,
                           @RequestParam(value = "filterDay", required = false) String filter,
                           @AuthenticationPrincipal User user,
                           @PageableDefault(sort = {"date", "id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        if (idEdit != null) {
            try {
                int idInt = Integer.parseInt(idEdit);
                model.addAttribute("idEdit", idInt);
            } catch (NumberFormatException ignore) {
            }
        }
        if (filter != null) {
            model.addAttribute("filterDay", filter);
        }
        setPage(model, user, pageable, filter);
        Date startDate = start == null ? null : DateUtil.getDate(start);
        Date endDate = end == null ? null : DateUtil.getDate(end);
        setCalculate(model, user, startDate, endDate);
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
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("expenses") Expenses expenses,
                       @RequestParam("dateS") String date,
                       @RequestParam("timeS") String time,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        expensesService.updateFormForm(expenses, DateUtil.getDate(date, time));
        redirectAttributes.addAttribute("idEdit", 0); // очишаем поле редактирования
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id,
                         @AuthenticationPrincipal User user,
                         RedirectAttributes redirectAttributes,
                         @RequestHeader(required = false) String referer) {
        expensesService.deleteByIdAndUser(id, user);
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
    }

    @GetMapping("/calc")
    public String calculate(Model model,
                            @RequestParam("calcStart") String start,
                            @RequestParam("calcEnd") String end,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer) {
        redirectAttributes.addAttribute("calcStart", start);
        redirectAttributes.addAttribute("calcEnd", end);
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
    }

    @GetMapping("/find")
    public String find(Model model,
                       @RequestParam("filterDay") String filter,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer) {
        redirectAttributes.addAttribute("filterDay", filter);
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
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
        return ControllerUtils.redirect(redirectAttributes, referer, URL_REDIRECT);
    }
}
