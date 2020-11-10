package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actions")
@Getter @Setter
@NoArgsConstructor
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "action", fetch = FetchType.EAGER)
    private List<ActionOfRights> actionOfRights;

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
