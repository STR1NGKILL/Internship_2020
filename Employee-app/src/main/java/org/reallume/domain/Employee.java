package org.reallume.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rights_id")
    private Rights rights;

    public Employee() { }

    public Employee(String firstName, String secondName, String patronymic, String login, String password, String salt) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

}
