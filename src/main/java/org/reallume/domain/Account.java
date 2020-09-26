package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Table(name = "accounts")
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

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getCurrencyQuantity() {
        return currencyQuantity;
    }

    public void setCurrencyQuantity(long currencyQuantity) {
        this.currencyQuantity = currencyQuantity;
    }

    public Calendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    public Calendar getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Calendar closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


}
