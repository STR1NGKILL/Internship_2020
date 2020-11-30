package org.reallume.repository.main;

import org.reallume.domain.main.Account;
import org.reallume.domain.main.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();
}
