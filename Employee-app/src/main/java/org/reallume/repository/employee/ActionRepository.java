package org.reallume.repository.employee;

import org.reallume.domain.employee.Action;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionRepository extends CrudRepository<Action, Long> {

    Optional<Action> findById(Long action_id);

    List<Action> findAll();

}
