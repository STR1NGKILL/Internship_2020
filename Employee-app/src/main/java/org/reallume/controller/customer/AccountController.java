package org.reallume.controller.customer;

import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.main.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class AccountController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping(value = "/accounts")
    public String accountsPage(Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("accounts", accountRepository.findAll());

        return "account/accounts-page";
    }

}
