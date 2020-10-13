package org.reallume.repository;

import org.reallume.domain.ActionOfRights;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionOfRightsRepository extends CrudRepository<ActionOfRights, Long> {

    Optional<ActionOfRights> findById(Long actionOfRights_id);

    List<ActionOfRights> findActionOfRightsByRights_Id(Long rights_id);

    List<ActionOfRights> findAll();

    void deleteByRights_Id(Long rights_id);

    void deleteByAction_Id(Long action_id);




}
