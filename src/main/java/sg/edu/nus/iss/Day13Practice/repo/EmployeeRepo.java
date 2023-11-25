package sg.edu.nus.iss.Day13Practice.repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.Day13Practice.model.Employee;

@Repository
public class EmployeeRepo {

  final String dirPath = "/Users/khairulimran/data"; // To create data folder.
  final String fileName = "employee.txt";
  private List<Employee> employees;

  // Constructor
  public EmployeeRepo() throws ParseException{
    if (employees == null) { // If employees is not initialised, need to initialise.
      employees = new ArrayList<>();
    } 

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // Imported using java.text.DateFormat
    Date dt = df.parse("1965-08-09"); // Throws an exception.

    // In this example, we are hard-coding the employee details.
    Employee employee = new Employee("Hsien Loong", "Lee", "leehsienloong@gov.sg", "91234567", 8500, dt, 272210);
    employees.add(employee);

    employee = new Employee("Pritam", "Singh", "pritam@wp.sg", "92345678", 7500, dt, 272210);
    employees.add(employee);

    employee = new Employee("Khairul", "Imran", "Kimran@gmail.com", "9191 9191", 3500, dt, 680680);
    employees.add(employee);
  }


  // Repository's Methods.

  public List<Employee> findAll() {
    return employees;
  }

  public Boolean delete(Employee employee) {
    Boolean result = false;
    int employeeIndex = employees.indexOf(employee);

    if (employeeIndex >= 0) { // Record exists
      employees.remove(employeeIndex);
      result = true;
    }
    return result;
  }

  public Employee findByEmail(String email) {
    return employees.stream().filter(emp -> emp.getEmail().equals(email)).findFirst().get(); // .get() converts it back to an employee object.
  }

  public Boolean updateEmployee(Employee employee) {
    Boolean result = false;

    // Retrieves the object originally in storage.
    Employee employeeObject = employees.stream().filter(emp -> emp.getEmail().equals(employee.getEmail())).findFirst().get();
    int employeeIndex = employees.indexOf(employeeObject);

    if (employeeIndex >= 0) { // Record exists.
      // performing the update.
      employees.get(employeeIndex).setBirthDay(employee.getBirthDay());
      employees.get(employeeIndex).setEmail(employee.getEmail());
      employees.get(employeeIndex).setFirstName(employee.getFirstName());
      employees.get(employeeIndex).setLastName(employee.getLastName());
      employees.get(employeeIndex).setPhoneNo(employee.getPhoneNo());
      employees.get(employeeIndex).setPostalCode(employee.getPostalCode());
      employees.get(employeeIndex).setSalary(employee.getSalary());
      result = true;
    }
    return result;
  }

  public Boolean save(Employee employee) throws FileNotFoundException {
    Boolean result = false;
    result = employees.add(employee);

    File f = new File(dirPath + "/" + fileName);
    OutputStream os = new FileOutputStream(f, true);
    PrintWriter pw = new PrintWriter(os);

    pw.println(employee.toString());
    pw.flush(); // To double confirm writing it.
    pw.close();

    return result;
  }
}
