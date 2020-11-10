package org.reallume.repository.main;

import org.reallume.domain.main.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
