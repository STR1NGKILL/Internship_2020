package org.reallume.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Byte id;

    @NotNull
    private String name;

    public Currency(){}

    public Currency(String name) {
        this.name = name;
    }

    public Byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
