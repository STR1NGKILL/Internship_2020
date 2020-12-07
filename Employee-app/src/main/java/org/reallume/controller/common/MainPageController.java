package org.reallume.controller.common;

import org.reallume.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;


@Controller
public class MainPageController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/main")
    public String mainPage(Authentication authentication, Model model){

        String login = authentication.getName();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("loggedEmployeeAuthorities", authentication.getAuthorities());
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(login).get());
        model.addAttribute("authorities", authorities);


        return "common/main-page";
    }

}
