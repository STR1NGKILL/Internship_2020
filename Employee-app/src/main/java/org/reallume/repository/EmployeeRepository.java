package org.reallume.repository;

import org.reallume.domain.Action;
import org.reallume.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByFirstNameAndSecondNameAndPatronymic(String firstName, String secondName, String patronymic);

    Optional<Employee> findById(Long employee_id);

    List<Employee> findAll();

}
