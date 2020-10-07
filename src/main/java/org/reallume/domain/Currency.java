package org.reallume.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "currencies")
@Getter @Setter
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

}
