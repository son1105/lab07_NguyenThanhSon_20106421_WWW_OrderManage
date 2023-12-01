package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.repositories.CustomerRepository;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public String getOne(@PathVariable("id") long id, Model model){
        Optional<Customer> otp = customerRepository.findById(id);
        if(otp.isPresent()){
            Customer customer = otp.get();
            model.addAttribute("customer", customer);
        }
        return "customer/customer";
    }
}
