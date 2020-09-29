package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Boolean status;

    @ManyToMany
    @JoinTable(
            name = "rights_actions",
            joinColumns = {@JoinColumn(name = "actions_id") },
            inverseJoinColumns = {@JoinColumn(name = "rights_id") }
    )
    private List<Rights> rights;

    public Action(){ }

    public Action(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = false;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Rights> getRights() {
        return rights;
    }

    public void setRights(List<Rights> rights) {
        this.rights = rights;
    }
}
