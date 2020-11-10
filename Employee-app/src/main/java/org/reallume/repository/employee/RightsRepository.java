package org.reallume.repository.employee;

import org.reallume.domain.employee.Rights;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RightsRepository extends CrudRepository<Rights, Long> {

    Rights findByName(String name);

    Optional<Rights> findById(Long rights_id);

    List<Rights> findAll();

    void deleteById(Long rights_id);
}
