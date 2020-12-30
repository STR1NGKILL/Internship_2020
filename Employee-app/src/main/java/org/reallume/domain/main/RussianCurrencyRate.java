package org.reallume.domain.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "russian_currency_rate")
@Getter
@Setter
@NoArgsConstructor
public class RussianCurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Byte id;

    @NotNull
    private String name;

    @NotNull
    private String sign;

    @NotNull
    private BigDecimal value;


    public RussianCurrencyRate(String name, String sign, BigDecimal value) {
        this.name = name;
        this.sign = sign;
        this.value = value;
    }
}
