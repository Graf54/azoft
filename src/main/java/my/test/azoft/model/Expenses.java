package my.test.azoft.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table
@ToString(exclude = "user")
@EqualsAndHashCode(of = "id")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    @JsonView(Views.UserExpenses.class)
    private Date date;

    @Column(length = 255)
    @JsonView(Views.UserExpenses.class)
    private String description;

    @Column(length = 255)
    @JsonView(Views.UserExpenses.class)
    private String comment;

    @JsonView(Views.UserExpenses.class)
    private BigDecimal value;
}
