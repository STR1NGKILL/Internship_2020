package org.reallume.domain.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;


@Entity
@Table(name = "accounts")
@Getter @Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String number;

    @NotNull
    @Column(name = "currency_quantity")
    private long currencyQuantity;

    @NotNull
    @Column(name = "open_date")
    private Calendar openDate;

    @NotNull
    @Column(name = "close_date")
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

    public Account(String number, Currency currency, long currencyQuantity, Calendar accountOpenDate, Calendar accountCloseDate, boolean accountStatus) {
        this.number = number;
        this.currency = currency;
        this.currencyQuantity = currencyQuantity;
        this.openDate = accountOpenDate;
        this.closeDate = accountCloseDate;
        this.status = accountStatus;
    }

}
