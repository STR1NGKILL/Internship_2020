package main.java.org.reallume.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import org.reallume.domain.employee.Account;
import org.reallume.domain.employee.Customer;

@Entity
@Table(name = "cards")
@Getter @Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String number;

    @NotNull
    private Calendar openDate;

    @NotNull
    private Calendar closeDate;

    @NotNull
    private boolean status;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Customer customer;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_id")
    private Account account;

    public Card(){}

    public Card(String number, Calendar openDate, Calendar closeDate, boolean status) {
        this.number = number;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
    }

}
