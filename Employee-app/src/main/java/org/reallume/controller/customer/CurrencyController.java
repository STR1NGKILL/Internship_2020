package org.reallume.controller.customer;

import org.reallume.domain.main.Currency;
import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.main.AccountRepository;
import org.reallume.repository.main.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.stream.Collectors;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;


    @GetMapping(value = "/currencies")
    public String currencyPage(Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("currencies", currencyRepository.findAll());

        return "currency/currencies-page";
    }


    @GetMapping(value = "/currencies/create")
    public String currencyCreatePage(Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("currency", new Currency());

        return "currency/create-page";
    }


    @PostMapping(value = "/currencies/create")
    public String currencyCreate(@ModelAttribute Currency currency) {

        currencyRepository.save(currency);

        return "redirect:/currencies";
    }


    @GetMapping(value = "/currencies/{currency_id}/edit")
    public String currencyEditPage(@PathVariable Integer currency_id, Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Currency currency = currencyRepository.findById(currency_id).get();

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("currency", currency);

        boolean accountWithCurrency;
        accountWithCurrency = accountRepository.findByCurrency_Id(currency_id).isPresent();

        model.addAttribute("accountWithCurrencyChecker", accountWithCurrency);

        return "currency/edit-page";
    }


    @PostMapping(value = "/currencies/{currency_id}/edit")
    public String editCurrency(@PathVariable Integer currency_id, @ModelAttribute Currency currency) {

        currency.setId(currencyRepository.findById(currency_id).get().getId());
        currencyRepository.save(currency);

        return "redirect:/currencies/" + currency_id.toString() + "/edit";
    }


    @GetMapping(value = "/currencies/{currency_id}/delete")
    public String deleteCurrency(@PathVariable Integer currency_id) {

        //so we need to find at least one account using the currency that has got that id
        if(accountRepository.findByCurrency_Id(currency_id).isEmpty())
            currencyRepository.deleteById(currency_id);

        return "redirect:/currencies";
    }



}
