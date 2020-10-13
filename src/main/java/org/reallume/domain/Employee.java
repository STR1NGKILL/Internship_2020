package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter @Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String  firstName,
                    secondName,
                    patronymic;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String salt;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rights_id")
    private Rights rights;


    public Employee() { }

    public Employee(String firstName, String secondName, String patronymic, String login, String password, String salt, Rights rights) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.rights = rights;
    }

}
