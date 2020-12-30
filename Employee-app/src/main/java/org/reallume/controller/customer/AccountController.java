package org.reallume.controller.customer;

import org.reallume.controller.common.SecurityController;
import org.reallume.domain.main.Account;
import org.reallume.domain.main.Customer;
import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.main.AccountRepository;
import org.reallume.repository.main.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
public class AccountController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    @GetMapping(value = "/accounts/create")
    public String createAccountPage(Authentication authentication, Model model) {

        String openDateString = "";
        String closeDateString = "";

        Account newAccount = new Account();
        newAccount.setStatus(true);

        String generatedNumber = SecurityController.generateAccountNumber(19);
        while(accountRepository.findByNumber(generatedNumber).isPresent())
            generatedNumber = SecurityController.generateAccountNumber(19);
        newAccount.setNumber(generatedNumber);

        newAccount.setAmount(BigDecimal.valueOf(0.0));


        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("openDateString", openDateString);
        model.addAttribute("closeDateString", closeDateString);
        model.addAttribute("account", newAccount);
        model.addAttribute("customers", customerRepository.findAll());


        return "account/create-page";
    }

    @PostMapping(value = "/accounts/create")
    public String createAccount(@ModelAttribute Account newAccount,
                                 @RequestParam String openDateString, @RequestParam String closeDateString, @RequestParam Long selectedCustomer) throws ParseException {

        newAccount.setOpenDate(converterStringToDate(openDateString));
        newAccount.setCloseDate(converterStringToDate(closeDateString));

        accountRepository.save(newAccount);

        return "redirect:/accounts";
    }

    public Date converterStringToDate(String StringValue) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(StringValue);
    }

    public String converterDateToString(Date dateValue) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(dateValue);
    }

}
