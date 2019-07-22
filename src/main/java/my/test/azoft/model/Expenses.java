package my.test.azoft.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn/*(nullable = false)*/
    private User user;
    @Column(nullable = false)
    private Date date;
    @Column(length = 255)
    private String description;
    @Column(length = 255)
    private String comment;
    private double value;
}
