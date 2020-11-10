package org.reallume.repository.main;

import org.reallume.domain.main.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {
}
