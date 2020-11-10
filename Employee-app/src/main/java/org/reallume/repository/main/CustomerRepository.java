package org.reallume.repository.main;

import org.reallume.domain.main.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
