package main.java.org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import org.reallume.domain.employee.Currency;
import org.reallume.domain.employee.Customer;

@Entity
@Table(name = "accounts")
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String number;

    @NotNull
    private long currencyQuantity;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="card_id")
    private Card card;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="currency_id")
    private Currency currency;

    public Account(){}

    public Account(String number, Currency currency, long currencyQuantity, Calendar accountOpenDate, Calendar accountCloseDate, boolean accountStatus) {
        this.number = number;
        this.currency = currency;
        this.currencyQuantity = currencyQuantity;
        this.openDate = accountOpenDate;
        this.closeDate = accountCloseDate;
        this.status = accountStatus;
    }

}
