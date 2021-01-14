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
    @Column(name = "currency_quantity")
    private long currencyQuantity;

    @NotNull
    @Column(name = "open_date")
    private Date openDate;

    @NotNull
    @Column(name = "close_date")
    private Date closeDate;

    @NotNull
    private Boolean status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="card_id")
    private Card card;

    @NotNull
    @OneToOne
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
