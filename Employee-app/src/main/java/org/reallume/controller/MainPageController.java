package org.reallume.controller;

import org.reallume.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainPageController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/main")
    public String mainPage(Authentication authentication, Model model) throws NoSuchFieldException {

        String login = authentication.getName();
        Boolean res = authentication.getAuthorities().contains("create_edit_delete_rights");


        model.addAttribute("loggedEmployeeAuthorities", authentication.getAuthorities());
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(login).get());
        return "common/main-page";
    }

}
