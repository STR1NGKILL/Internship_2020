package org.reallume.repository.main;

import org.reallume.domain.main.Currency;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Byte> {

    List<Currency> findAll();

    Optional<Currency> findById(Integer id);

    @Transactional
    @Modifying
    @Query("delete from Currency c where c.id=:currency_id")
    void deleteById(@Param("currency_id")Integer id);

}
