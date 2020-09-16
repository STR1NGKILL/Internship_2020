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
    private byte currencyNumber;

    @NotNull
    private long currencyQuantity;

    @NotNull
    private Calendar openDate;

    @NotNull
    private Calendar closeDate;

    @NotNull
    private boolean status;

    public Account(){}

    public Account(String number, byte currencyNumber, long currencyQuantity, Calendar accountOpenDate, Calendar accountCloseDate, boolean accountStatus) {
        this.number = number;
        this.currencyNumber = currencyNumber;
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

    public byte getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(byte currencyNumber) {
        this.currencyNumber = currencyNumber;
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
}
