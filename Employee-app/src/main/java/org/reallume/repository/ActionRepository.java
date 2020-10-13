package org.reallume.repository;

import org.reallume.domain.Action;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionRepository extends CrudRepository<Action, Long> {

    Optional<Action> findById(Long action_id);

    List<Action> findAll();

}
