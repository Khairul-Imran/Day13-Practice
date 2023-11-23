package sg.edu.nus.iss.Day13Practice.controller;

import java.io.FileNotFoundException;
import java.util.List;

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


  @PostMapping("/saveEmployee")
  public String saveEmployee(@Valid @ModelAttribute("employee") Employee employeeForm, BindingResult result, Model model) throws FileNotFoundException { // This sequence is important.... allows you to pass an entire object.
    // Result is the result in the form.
    // Valid is for validating the parameter (according to the Employee model) you received before using it.
    // ModelAtribute -> take the attribute "employee", and place it inside the employee form
    // Model allows you to keep passing the obj to another form if needed.

    // Detect if there is an error.
    if(result.hasErrors()) {
      return "employeeadd"; // Stays at the same page if there is an error.
    }

    Boolean returnResult = employeeRepo.save(employeeForm);

    //return "redirect:/employees/list"; // "/employees/list" is a controller endpoint.
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
  
}
