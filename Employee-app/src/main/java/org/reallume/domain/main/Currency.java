package org.reallume.domain.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    @NotNull
    private String sign;

    @NotNull
    private BigDecimal value;

    public Currency(String name, String sign, BigDecimal value) {
        this.name = name;
        this.sign = sign;
        this.value = value;
    }
}
