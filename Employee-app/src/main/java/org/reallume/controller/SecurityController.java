package org.reallume.controller;

/*import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/

import org.reallume.domain.Employee;
import org.reallume.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class SecurityController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        return "common/login-page";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        Model model) {

        if(employeeRepository.findByLogin(login).isPresent()){
            Employee foundEmployee = employeeRepository.findByLogin(login).get();
            if((password + foundEmployee.getSalt()).equals(foundEmployee.getPassword())) {
                model.addAttribute("loggedEmployee", foundEmployee);
                return "common/main-page";
            }
        }
        return "common/login-page";
    }

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    public static String generateSalt(){
        return UUID.randomUUID().toString();
    }

    public static String getSaltPassword(String password, String salt){

        /*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password + salt);*/
        return password + salt;
    }



}
