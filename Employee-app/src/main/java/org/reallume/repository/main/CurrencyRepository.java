package org.reallume.repository.main;

import org.reallume.domain.main.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Byte> {

    List<Currency> findAll();

    Optional<Currency> findById(Byte id);
}
