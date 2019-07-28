package my.test.azoft.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.test.azoft.model.Expenses;
import my.test.azoft.model.Views;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.UserExpenses.class)
public class ExpensesPageDto {
    private List<Expenses> expenses;
    private int currentPage;
    private int totalPages;
}
