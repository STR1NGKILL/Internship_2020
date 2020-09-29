package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }
}
