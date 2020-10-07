package org.reallume.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "rights")
@Getter @Setter
public class Rights {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    /*@ManyToMany
    @JoinTable(
            name = "rights_actions",
            joinColumns = {@JoinColumn(name = "rights_id") },
            inverseJoinColumns = {@JoinColumn(name = "actions_id"), @JoinColumn(name = "status") }
    )*/
    @OneToMany(mappedBy = "rights", fetch = FetchType.EAGER)
    private List<ActionOfRights> actionOfRights;

    public Rights() { }

    public Rights(String name) {
        this.name = name;
    }

}
