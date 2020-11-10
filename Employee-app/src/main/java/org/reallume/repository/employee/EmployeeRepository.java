package org.reallume.repository.employee;

import org.reallume.domain.employee.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByFirstNameAndSecondNameAndPatronymic(String firstName, String secondName, String patronymic);

    Optional<Employee> findById(Long employee_id);

    Optional<Employee> findByLogin(String employee_login);

    Optional<Employee> findByRights_Id(Long rights_id);

    List<Employee> findAll();

    void deleteById(Long employee_id);

}
