package service;

import model.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);

    void updateEmployeeById(Employee employee);

    void deleteEmployeeById(int emp_id);

    List<Employee> getAllEmployees(int page, int pageSize);

    List<Employee> searchEmployeeByPosition(String position);

    List<Employee> searchEmployeeBySalary(BigDecimal salary);

    List<Employee> searchEmployeeByHireDate(String hire_date);

    List<Employee> topSalaryEmployees(int page, int pageSize);

    void KPIByAge();

    void KPIByHireDate();
}
