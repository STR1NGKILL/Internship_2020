package org.reallume.controller.employee;

import lombok.Getter;
import lombok.Setter;
import org.reallume.controller.common.SecurityController;
import org.reallume.domain.employee.Employee;
import org.reallume.domain.employee.Rights;
import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.employee.RightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/employees")
    public String employeesPage(Model model, Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("employees", employeeRepository.findAll());

        return "employee/employees-page";
    }

    @GetMapping(value = "/employees/create")
    public String createEmployeePage(Model model, Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("newEmployee", new Employee());

        return "employee/create-page";
    }

    @PostMapping(value = "/employees/create")
    public String createEmployee(@ModelAttribute Employee newEmployee, @RequestParam Long selectedRights) {

        newEmployee.setSalt(SecurityController.generateSalt());
        newEmployee.setPassword(SecurityController.getSaltPassword(newEmployee.getPassword(), newEmployee.getSalt()));
        newEmployee.setActivity(true);

        newEmployee.setRights(rightsRepository.findById(selectedRights).get());

        employeeRepository.save(newEmployee);

        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/{employee_id}/edit")
    public String editEmployeePage(@PathVariable Long employee_id, Model model, Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("authorities", authorities);
        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("currentEmployee", employeeRepository.findById(employee_id).get());

        return "employee/edit-page";
    }

    @PostMapping(value = "/employees/{employee_id}/edit")
    public String editEmployee(@PathVariable Long employee_id,
                               @ModelAttribute("currentEmployee") CurrentEmployee currentEmployee,
                               @RequestParam Long selectedRights) {

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

    @GetMapping(value = "/employees/{employee_id}/delete")
    public String deleteEmployee(@PathVariable Long employee_id) {

        employeeRepository.deleteById(employee_id);

        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/{employee_id}/activity/set")
    public String setActivityEmployee(@PathVariable Long employee_id) {

        Employee employeeToEdit = employeeRepository.findById(employee_id).get();

        employeeToEdit.setActivity(!employeeToEdit.getActivity().equals(true));

        employeeRepository.save(employeeToEdit);

        return "redirect:/employees/" + employee_id.toString() + "/edit";
    }

}
