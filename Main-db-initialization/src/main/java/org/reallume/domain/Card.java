package org.reallume.domain;

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
    @Column(name = "active_status")
    private Boolean activeStatus;

    @NotNull
    @Column(name = "block_status")
    private Boolean blockStatus;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Card(String number, Date openDate, Date closeDate, Boolean activeStatus, Boolean blockStatus) {
        this.number = number;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.activeStatus = activeStatus;
        this.blockStatus = blockStatus;
    }

    public Card(String number, Date openDate, Date closeDate) {
        this.number = number;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.activeStatus = true;
        this.blockStatus = false;
    }

}
