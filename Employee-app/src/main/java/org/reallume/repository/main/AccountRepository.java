package org.reallume.repository.main;

import org.reallume.domain.main.Account;
import org.reallume.domain.main.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();

    Optional<Account> findByNumber(String number);

    Optional<Account> findById(Long id);

    List<Account> findByCustomerAndStatus(Customer customer, Boolean status);

    void deleteById(Long id);

}
