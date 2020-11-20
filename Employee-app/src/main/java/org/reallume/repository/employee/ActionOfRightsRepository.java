package org.reallume.repository.employee;

import org.reallume.domain.employee.ActionOfRights;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActionOfRightsRepository extends CrudRepository<ActionOfRights, Long> {

    Optional<ActionOfRights> findById(Long actionOfRights_id);

    Optional<ActionOfRights> findByRights_IdAndAction_Id(Long rights_id, Long action_id);

    List<ActionOfRights> findByRights_id(Long rights_id);

    List<ActionOfRights> findAll();

    void deleteByRights_Id(Long rights_id);

}
