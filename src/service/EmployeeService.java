package service;

import model.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);

    void updateEmployeeById(String emp_id, Employee employee);

    void deleteEmployeeById(Employee employee);

    List<Employee> getAllEmployees();

    void searchEmployeeByPosition(String position);

    void searchEmployeeBySalary(BigDecimal salary);

    void searchEmployeeByHireDate(String hire_date);
}
