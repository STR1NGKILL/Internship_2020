package org.reallume.controller;

import org.reallume.domain.Employee;
import org.reallume.repository.EmployeeRepository;
import org.reallume.repository.RightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeePageController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RightsRepository rightsRepository;

    @GetMapping(value = "/employees")
    public String rightsPage(Model model) {

        List<Employee> employees = employeeRepository.findAll();

        Employee newEmployee = new Employee();

        model.addAttribute("employees", employees);
        model.addAttribute("allRights", rightsRepository.findAll());
        model.addAttribute("newEmployee", newEmployee);

        return "employee-page";
    }

    @PostMapping(value = "/employees/create")
    public String createEmployee(@ModelAttribute Employee newEmployee, @RequestParam Long selectedRights) {

        newEmployee.setSalt("newSalt123");

        newEmployee.setRights(rightsRepository.findById(selectedRights).get());

        employeeRepository.save(newEmployee);

        return "redirect:/employees";
    }

}
