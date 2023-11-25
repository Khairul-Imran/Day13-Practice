package sg.edu.nus.iss.Day13Practice.controller;

import java.io.FileNotFoundException;
import java.util.List;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.Day13Practice.model.Employee;
import sg.edu.nus.iss.Day13Practice.repo.EmployeeRepo;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

  @Autowired // Automatically instantiates your employeeRepo (beans).
  EmployeeRepo employeeRepo;

  @GetMapping("/list")
  public String employeeList(Model model) {
    List<Employee> employees = employeeRepo.findAll();

    model.addAttribute("employees", employees); // Refers to "emp: ${employees}" in the employeelist.

    return "employeelist";
  }


  @GetMapping("/addnew")
  public String employeeAdd(Model model) {
    Employee employee = new Employee();
    model.addAttribute("employee", employee);

    return "employeeadd";
  }


  @PostMapping("/saveEmployee") // This sequence is important in allowing you to pass an entire object.
  public String saveEmployee(@Valid @ModelAttribute("employee") Employee employeeForm, BindingResult result, Model model) throws FileNotFoundException {

    if(result.hasErrors()) {
      return "employeeadd"; // Stays at the same page.
    }

    Boolean returnResult = employeeRepo.save(employeeForm);

    //return "redirect:/employees/list"; // -> This is an alternative template page you could direct to.
    model.addAttribute("savedEmployee", employeeForm);
    return "success";
  }

  @GetMapping("/employeedelete/{email}") // Hyperlink -> GetMapping always.
  public String deleteEmployee(@PathVariable("email") String email) {
    // Checking if the employee exists.
    Employee employee = employeeRepo.findByEmail(email);

    Boolean bResult = employeeRepo.delete(employee);
    return "redirect:/employees/list";
  }

  @GetMapping("/employeeupdate/{email}")
  public String updateEmployee(@PathVariable("email") String email, Model model) {

    Employee employee = employeeRepo.findByEmail(email);
    model.addAttribute("employee", employee);

    return "employeeupdate";
  }

  @PostMapping("/updEmployee")
  public String updateEmployeeRecord(@ModelAttribute("employee") Employee employee, BindingResult result, Model model) {

    if (result.hasErrors()) {
      return "employeeupdate";
    }

    employeeRepo.updateEmployee(employee);
    return "redirect:/employees/list";
  }
  
}
