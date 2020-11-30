package org.reallume.repository.main;

import org.reallume.domain.main.Card;
import org.reallume.domain.main.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findAll();
}
