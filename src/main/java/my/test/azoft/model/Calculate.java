package my.test.azoft.model;

import lombok.Data;

import java.util.Date;

// класс для расчета средних значений расходов
@Data
public class Calculate {

    private Date start;
    private Date end;
    private double total;
    private double average;
}
