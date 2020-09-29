package org.reallume.repository;

import org.reallume.domain.Action;
import org.reallume.domain.Rights;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionRepository extends CrudRepository<Action, Long> {

    Action findByName(String name);

    Optional<Action> findById(Long rights_id);

    List<Action> findAll();

}
