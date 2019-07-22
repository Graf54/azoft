package my.test.azoft.model;

import lombok.Data;

import java.util.Date;

@Data
public class Calculate {
    private Date start;
    private Date end;
    private double total;
}
