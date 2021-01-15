package org.reallume.controller.customer;

import org.reallume.controller.common.SecurityController;
import org.reallume.domain.main.Account;
import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.main.AccountRepository;
import org.reallume.repository.main.CurrencyRepository;
import org.reallume.repository.main.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping(value = "/accounts")
    public String accountsPage(Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("accounts", accountRepository.findAll());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        model.addAttribute("dateFormat", dateFormat);

        return "account/accounts-page";
    }

    @GetMapping(value = "/accounts/create")
    public String createAccountPage(Authentication authentication, Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String openDateString = dateFormat.format(new Date());
        String closeDateString = "";
        Long selectedCustomerId = 0L, selectedCurrencyId = 0L;

        Account newAccount = new Account();

        String generatedNumber = SecurityController.generateNumber(19);
        while(accountRepository.findByNumber(generatedNumber).isPresent())
            generatedNumber = SecurityController.generateNumber(19);
        newAccount.setNumber(generatedNumber);

        newAccount.setAmount(BigDecimal.valueOf(0.0));
        newAccount.setStatus(true);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("openDateString", openDateString);
        model.addAttribute("closeDateString", closeDateString);
        model.addAttribute("selectedCustomer", selectedCustomerId);
        model.addAttribute("selectedCurrency", selectedCurrencyId);
        model.addAttribute("account", newAccount);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("currencies", currencyRepository.findAll());

        return "account/create-page";
    }

    @PostMapping(value = "/accounts/create")
    public String createAccount(@ModelAttribute Account newAccount,
                                 @RequestParam String openDateString, @RequestParam String closeDateString, @RequestParam Long selectedCustomer, @RequestParam Integer selectedCurrency) throws ParseException {

        newAccount.setOpenDate(converterStringToDate(openDateString));
        newAccount.setCloseDate(converterStringToDate(closeDateString));
        newAccount.setCustomer(customerRepository.findById(selectedCustomer).get());
        newAccount.setCurrency(currencyRepository.findById(selectedCurrency).get());
        newAccount.setStatus(true);

        accountRepository.save(newAccount);

        return "redirect:/accounts";
    }

    @GetMapping(value = "/accounts/{account_id}/edit")
    public String editAccountPage(@PathVariable Long account_id, Authentication authentication, Model model) {

        Account account = accountRepository.findById(account_id).get();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String openDateString = dateFormat.format(account.getOpenDate());
        String closeDateString = dateFormat.format(account.getCloseDate());

        Integer selectedCurrencyId = account.getCurrency().getId();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("openDateString", openDateString);
        model.addAttribute("closeDateString", closeDateString);
        model.addAttribute("selectedCustomer", account_id);
        model.addAttribute("selectedCurrency", selectedCurrencyId);
        model.addAttribute("account", account);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("currencies", currencyRepository.findAll());

        return "account/edit-page";
    }

    @PostMapping(value = "/accounts/{account_id}/edit")
    public String editAccount(@PathVariable Long account_id,
                                @RequestParam String openDateString, @RequestParam String closeDateString, @RequestParam Long selectedCustomer, @RequestParam Byte selectedCurrency) throws ParseException {

        Account originAccount = accountRepository.findById(account_id).get();

        originAccount.setOpenDate(converterStringToDate(openDateString));
        originAccount.setCloseDate(converterStringToDate(closeDateString));
        originAccount.setCurrency(currencyRepository.findById(selectedCurrency).get());
        originAccount.setCustomer(customerRepository.findById(selectedCustomer).get());

        accountRepository.save(originAccount);

        return "redirect:/accounts/" + account_id.toString() + "/edit";
    }

    @GetMapping(value = "/accounts/{account_id}/change_status")
    public String changeAccountsStatus(@PathVariable Long account_id) {

        Account account = accountRepository.findById(account_id).get();

        account.setStatus(!account.getStatus());

        accountRepository.save(account);

        return "redirect:/accounts/" + account_id.toString() + "/edit";
    }

    @Transactional
    @GetMapping(value = "/accounts/{account_id}/delete")
    public String deleteAccount(@PathVariable Long account_id) {

        accountRepository.deleteById(account_id);

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
