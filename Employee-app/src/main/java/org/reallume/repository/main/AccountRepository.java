package org.reallume.repository.main;

import org.reallume.domain.main.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();
}
