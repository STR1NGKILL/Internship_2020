package org.reallume.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "clients")
public class Client {

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

    public Client(){}

    public Client(String firstName, String secondName, String patronymic, byte[] document, Calendar birthday) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.document = document;
        this.birthday = birthday;
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

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }
}
