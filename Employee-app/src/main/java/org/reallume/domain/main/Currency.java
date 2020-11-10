package org.reallume.domain.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "currencies")
@Getter @Setter
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Byte id;

    @NotNull
    private String name;

    public Currency(String name) {
        this.name = name;
    }

}
