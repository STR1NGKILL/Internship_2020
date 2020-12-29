package org.reallume.repository.main;

import org.reallume.domain.main.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();

    Optional<Customer> findByLogin(String login);
}
