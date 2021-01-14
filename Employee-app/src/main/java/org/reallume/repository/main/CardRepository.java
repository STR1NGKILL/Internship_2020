package org.reallume.repository.main;

import org.reallume.domain.main.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findAll();

    Optional<Card> findByNumber(String number);

    Optional<Card> findByNumberAndActiveStatus(String number, Boolean status);

    Optional<Card> findById(Long id);

    void deleteById(Long id);

}
