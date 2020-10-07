package org.reallume.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String  firstName,
            secondName,
            patronymic;

    @NotNull
    @Lob
    private byte[] document;

    @NotNull
    private Calendar birthday;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;


    public Customer(){}

    public Customer(String firstName, String secondName, String patronymic, byte[] document, Calendar birthday) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.document = document;
        this.birthday = birthday;
    }

}
