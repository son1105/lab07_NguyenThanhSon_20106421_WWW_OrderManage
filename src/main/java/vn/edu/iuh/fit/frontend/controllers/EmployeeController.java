package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.repositories.EmployeeRepository;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping("/{id}")
    public String getOne(@PathVariable("id") long id, Model model){
        Optional<Employee> otp = employeeRepository.findById(id);
        if(otp.isPresent()){
            Employee employee = otp.get();
            model.addAttribute("employee", employee);
        }
        return "employee/employee";
    }
}
