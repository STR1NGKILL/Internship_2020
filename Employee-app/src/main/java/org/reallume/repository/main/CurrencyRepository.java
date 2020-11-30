package org.reallume.repository.main;

import org.reallume.domain.main.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    List<Currency> findAll();
}
