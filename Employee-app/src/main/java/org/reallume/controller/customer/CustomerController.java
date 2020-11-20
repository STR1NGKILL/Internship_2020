package org.reallume.controller.customer;

import org.reallume.domain.employee.Employee;
import org.reallume.domain.main.Customer;
import org.reallume.repository.main.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    //customer main page
    @GetMapping(value = "/customers")
    public String rightsPage(Model model) {

        model.addAttribute("customers", customerRepository.findAll());

        return "customer/customers-page";
    }

    //create a customer - page
    @GetMapping(value = "/customers/create")
    public String createCustomerPage(Model model) {

        model.addAttribute("newCustomer", new Customer());

        return "customer/create-page";
    }


    @PostMapping(value = "/customers/create")
    public String createCustomer(@ModelAttribute Customer newCustomer){

        customerRepository.save(newCustomer);

        return "redirect:/customers";
    }

    //edit a customer - page
    @GetMapping(value = "/customers/{customer_id}/edit")
    public String editCustomerPage(@PathVariable Long customer_id, Model model) {

        model.addAttribute("currentCustomer", customerRepository.findById(customer_id).get());

        return "customer/edit-page";
    }

    @PostMapping(value = "/customers/{customer_id}/edit")
    public String editCustomer(@PathVariable Long customer_id,
                               @ModelAttribute("currentCustomer") Customer currentCustomer) {

        Customer customerToEdit = customerRepository.findById(customer_id).get();

        customerRepository.save(customerToEdit);

        return "redirect:/customers/" + customer_id.toString() + "/edit";
    }

    @Transactional
    @GetMapping(value = "/customers/{customer_id}/delete")
    public String deleteCustomer(@PathVariable Long customer_id) {

        customerRepository.deleteById(customer_id);

        return "redirect:/customers";
    }

}
