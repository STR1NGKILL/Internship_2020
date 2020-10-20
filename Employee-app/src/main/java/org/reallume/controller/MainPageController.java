package org.reallume.controller;

import org.reallume.domain.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class MainPageController {

    @GetMapping(value = "/main")
    public String mainPage(@ModelAttribute Employee loggedEmployee, Model model) {
        if(loggedEmployee.getId() == null)
            return "common/access-denied-page";
        return "common/main-page";
    }

}
