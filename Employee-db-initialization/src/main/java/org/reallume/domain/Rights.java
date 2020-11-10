package org.reallume.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "rights")
@Getter @Setter
@NoArgsConstructor
public class Rights {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "rights", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;

    @OneToMany(mappedBy = "rights", fetch = FetchType.EAGER)
    private List<ActionOfRights> actionOfRights;

    public Rights(String name) {
        this.name = name;
    }

}
