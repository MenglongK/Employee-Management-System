package service;

import model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    void addEmployee(Employee employee);

    void updateEmployeeById(int code ,Employee employee);

    Optional<Employee> findById(int empId);

    void deleteEmployeeById(int emp_id);

    List<Employee> getAllEmployees();

    List<Employee> searchEmployeeByPosition(String position);

    List<Employee> searchEmployeeBySalary(BigDecimal salary);

    List<Employee> searchEmployeeByHireDate(LocalDate hire_date);

    List<Employee> topSalaryEmployees();

    List<Employee> filterEmployeeByAge();

    List<Employee> KPIByAge();

    List<Employee> KPIByHireDate();
}
