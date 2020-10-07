package org.reallume.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "actions")
@Getter @Setter
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    /*@ManyToMany
    @JoinTable(
            name = "rights_actions",
            joinColumns = {@JoinColumn(name = "actions_id"), @JoinColumn(name = "status") },
            inverseJoinColumns = {@JoinColumn(name = "rights_id") }
    )*/
    @OneToMany(mappedBy = "action", fetch = FetchType.EAGER)
    private List<ActionOfRights> actionOfRights;

    public Action(){ }

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
