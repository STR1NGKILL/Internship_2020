package org.reallume.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.reallume.controller.employee.EmployeeController;
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

import java.util.stream.Collectors;

@Controller
public class ProfileController {

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

    @GetMapping(value = "/profile/edit")
    public String editEmployeePage(Model model, Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("authorities", authorities);
        model.addAttribute("allRights", rightsRepository.findAll());

        return "common/edit-profile-page";
    }

    @PostMapping(value = "/profile/edit")
    public String editEmployee(@ModelAttribute("loggedEmployee") ProfileController.CurrentEmployee currentEmployee,
                               @RequestParam Long selectedRights, Authentication authentication) {

        Employee employeeToEdit = employeeRepository.findByLogin(authentication.getName()).get();
        employeeToEdit.setFirstName(currentEmployee.getFirstName());
        employeeToEdit.setSecondName(currentEmployee.getSecondName());
        employeeToEdit.setPatronymic(currentEmployee.getPatronymic());
        employeeToEdit.setLogin(currentEmployee.getLogin());
        employeeToEdit.setPassword(SecurityController.getSaltPassword(currentEmployee.getPassword(), currentEmployee.getSalt()));
        employeeToEdit.setRights(rightsRepository.findById(selectedRights).get());

        employeeRepository.save(employeeToEdit);

        return "redirect:/profile/edit";
    }

    @GetMapping(value = "/profile/delete")
    public String deleteEmployee(Authentication authentication) {

        employeeRepository.deleteById(employeeRepository.findByLogin(authentication.getName()).get().getId());

        return "redirect:/login";
    }

}
