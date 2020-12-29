package org.reallume.domain.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "cards")
@Getter @Setter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String number;

    @NotNull
    @Column(name = "open_date")
    private Date openDate;

    @NotNull
    @Column(name = "close_date")
    private Date closeDate;

    @NotNull
    private boolean status;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Customer customer;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_id")
    private Account account;

    public Card(String number, Date openDate, Date closeDate, boolean status) {
        this.number = number;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.status = status;
    }

}
