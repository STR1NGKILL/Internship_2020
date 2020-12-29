package org.reallume.controller.customer;

import org.reallume.controller.common.SecurityController;
import org.reallume.domain.main.Customer;
import org.reallume.repository.employee.EmployeeRepository;
import org.reallume.repository.main.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;


@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/customers")
    public String rightsPage(Authentication authentication, Model model) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("customers", customerRepository.findAll());

        return "customer/customers-page";
    }

    @GetMapping(value = "/customers/create")
    public String createCustomerPage(Authentication authentication, Model model) {

        String birthdayStringValue = "";

        Customer newCustomer = new Customer();

        String generatedLogin = SecurityController.generateLogin();

        while(customerRepository.findByLogin(generatedLogin).isPresent())
            generatedLogin = SecurityController.generateLogin();

        newCustomer.setLogin(generatedLogin);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("birthdayStringValue", birthdayStringValue);
        model.addAttribute("newCustomer", newCustomer);

        return "customer/create-page";
    }

    @PostMapping(value = "/customers/create")
    public String createCustomer(@ModelAttribute Customer newCustomer,
                                 @RequestParam String birthdayStringValue,
                                 @Valid @RequestParam("file") MultipartFile file) throws ParseException, IOException {

        newCustomer.setDocument(file.getBytes());
        newCustomer.setBirthday(converterStringToDate(birthdayStringValue));

        String salt = SecurityController.generateSalt();

        newCustomer.setSalt(salt);
        newCustomer.setPassword(SecurityController.getSaltPassword(SecurityController.generatePassword(), salt));

        customerRepository.save(newCustomer);

        return "redirect:/customers";
    }

    @GetMapping(value = "/customers/{customer_id}/edit")
    public String editCustomerPage(@PathVariable Long customer_id, Model model, Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedEmployee", employeeRepository.findByLogin(authentication.getName()).get());
        model.addAttribute("birthdayStringValue", converterDateToString(customerRepository.findById(customer_id).get().getBirthday()));
        model.addAttribute("currentCustomer", customerRepository.findById(customer_id).get());

        return "customer/edit-page";
    }

    @PostMapping(value = "/customers/{customer_id}/edit")
    public String editCustomer(@PathVariable Long customer_id,
                               @ModelAttribute("currentCustomer") Customer currentCustomer,
                               @RequestParam String birthdayStringValue,
                               @Valid @RequestParam("file") MultipartFile file) throws ParseException, IOException {

        Customer customerToEdit = customerRepository.findById(customer_id).get();

        if (!file.isEmpty()) currentCustomer.setDocument(file.getBytes());
        else currentCustomer.setDocument(customerToEdit.getDocument());

        currentCustomer.setBirthday(converterStringToDate(birthdayStringValue));
        currentCustomer.setId(customer_id);
        currentCustomer.setLogin(customerToEdit.getLogin());
        currentCustomer.setPassword(customerToEdit.getPassword());
        currentCustomer.setSalt(customerToEdit.getSalt());
        currentCustomer.setAccounts(customerToEdit.getAccounts());
        currentCustomer.setCards(customerToEdit.getCards());

        customerRepository.save(currentCustomer);

        return "redirect:/customers/" + customer_id.toString() + "/edit";
    }

    @GetMapping(value = "/customers/{customer_id}/delete")
    public String deleteCustomer(@PathVariable Long customer_id) {

        customerRepository.deleteById(customer_id);

        return "redirect:/customers";
    }

    @GetMapping(value = "/customers/{customer_id}/edit/file/download")
    public HttpEntity<byte[]> downloadDocument(@PathVariable Long customer_id) {

        byte[] file = customerRepository.findById(customer_id).get().getDocument();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(file.length);

        return new HttpEntity<byte[]>(file, headers);
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
