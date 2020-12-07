package org.reallume.domain.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String  firstName;

    @NotNull
    @Column(name = "second_name")
    private String  secondName;

    @NotNull
    private String  patronymic;

    @NotNull
    @Lob
    private byte[] document;

    @NotNull
    private Date birthday;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String salt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    public Customer(String firstName, String secondName, String patronymic, byte[] document, Date birthday, String login, String password, String salt) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.document = document;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

}
