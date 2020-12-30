package org.reallume.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


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
    private long currencyQuantity;

    @NotNull
    private Date openDate;

    @NotNull
    private Date closeDate;

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

    @NotNull
    private BigDecimal amount;

    public Account(String number, Currency currency, long currencyQuantity, Date accountOpenDate, Date accountCloseDate, boolean accountStatus, BigDecimal amount) {
        this.number = number;
        this.currency = currency;
        this.currencyQuantity = currencyQuantity;
        this.openDate = accountOpenDate;
        this.closeDate = accountCloseDate;
        this.status = accountStatus;
        this.amount = amount;
    }

}
