package org.reallume.controller;

import lombok.Getter;
import lombok.Setter;
import org.reallume.domain.Employee;
import org.reallume.domain.Rights;
import org.reallume.repository.EmployeeRepository;
import org.reallume.repository.RightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RightsRepository rightsRepository;

    @Setter
    @Getter
    private static class CurrentEmployee {

        private String  firstName,
                secondName,
                patronymic;

        private String login;

        private String password;

        private String salt;

        private Rights rights;

        public CurrentEmployee() { }

        public CurrentEmployee(String firstName, String secondName, String patronymic, String login, String password, String salt, Rights rights) {
            this.firstName = firstName;
            this.secondName = secondName;
            this.patronymic = patronymic;
            this.login = login;
            this.password = password;
            this.salt = salt;
            this.rights = rights;
        }

    }

    //employee main page
    @GetMapping(value = "/employees")
    public String rightsPage(Model model) {

        model.addAttribute("employees", employeeRepository.findAll());

        return "employee/employee-page";
    }

    //create an employee page
    @GetMapping(value = "/employees/create")
    public String createEmployeePage(Model model) {

        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("newEmployee", new Employee());

        return "employee/create-page";
    }


    @PostMapping(value = "/employees/create")
    public String createEmployee(@ModelAttribute Employee newEmployee, @RequestParam Long selectedRights) throws NoSuchAlgorithmException {

        newEmployee.setSalt(SecurityController.generateSalt());
        newEmployee.setPassword(SecurityController.getSaltPassword(newEmployee.getPassword(), newEmployee.getSalt()));

        newEmployee.setRights(rightsRepository.findById(selectedRights).get());

        employeeRepository.save(newEmployee);

        return "redirect:/employees";
    }

    //edit an employee page
    @GetMapping(value = "/employees/{employee_id}/edit")
    public String editEmployeePage(@PathVariable Long employee_id, Model model) {

        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("currentEmployee", employeeRepository.findById(employee_id).get());

        return "employee/edit-page";
    }

    @PostMapping(value = "/employees/{employee_id}/edit")
    public String editEmployee(@PathVariable Long employee_id,
                               @ModelAttribute("currentEmployee") CurrentEmployee currentEmployee,
                               @RequestParam Long selectedRights) throws NoSuchAlgorithmException {

        Employee employeeToEdit = employeeRepository.findById(employee_id).get();
        employeeToEdit.setFirstName(currentEmployee.getFirstName());
        employeeToEdit.setSecondName(currentEmployee.getSecondName());
        employeeToEdit.setPatronymic(currentEmployee.getPatronymic());
        employeeToEdit.setLogin(currentEmployee.getLogin());
        employeeToEdit.setPassword(SecurityController.getSaltPassword(currentEmployee.getPassword(), currentEmployee.getSalt()));
        employeeToEdit.setRights(rightsRepository.findById(selectedRights).get());

        employeeRepository.save(employeeToEdit);

        return "redirect:/employees/" + employee_id.toString() + "/edit";
    }

    @Transactional
    @GetMapping(value = "/employees/{employee_id}/delete")
    public String deleteEmployee(@PathVariable Long employee_id) {

        employeeRepository.deleteById(employee_id);

        return "redirect:/employees";
    }

}
