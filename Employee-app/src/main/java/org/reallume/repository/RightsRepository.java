package org.reallume.repository;

import org.reallume.domain.Rights;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RightsRepository extends CrudRepository<Rights, Long> {

    Rights findByName(String name);

    Optional<Rights> findRightsById(Long rights_id);

    List<Rights> findAll();

    void deleteById(Long rights_id);
}
