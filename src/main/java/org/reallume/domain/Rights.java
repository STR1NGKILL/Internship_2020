package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "rights")
public class Rights {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

  //@ManyToMany(mappedBy = "rights", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToMany
    @JoinTable(
            name = "rights_actions",
            joinColumns = {@JoinColumn(name = "rights_id") },
            inverseJoinColumns = {@JoinColumn(name = "actions_id") }
    )
    private List<Action> actions;


    public Rights() { }

    public Rights(String name, List<Action> actions) {
        this.name = name;
        this.setActions(actions);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }



}
