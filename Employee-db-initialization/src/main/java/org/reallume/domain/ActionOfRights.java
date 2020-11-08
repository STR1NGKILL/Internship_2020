package org.reallume.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "action_of_rights")
@Getter @Setter
public class ActionOfRights {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rights_id")
    private Rights rights;

    @ManyToOne
    @JoinColumn(name = "actions_id")
    private Action action;

    private Boolean status;

    public ActionOfRights() {
        this.status = false;
    }

    public ActionOfRights(Action action, Rights rights) {
        this.action = action;
        this.rights = rights;
        this.status = false;
    }

    public ActionOfRights(Action action, Rights rights, Boolean status) {
        this.action = action;
        this.rights = rights;
        this.status = status;
    }

}
