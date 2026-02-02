package service;

import model.Employee;

import java.math.BigDecimal;
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

    List<Employee> searchEmployeeByHireDate(String hire_date);

    List<Employee> topSalaryEmployees(int page, int pageSize);

    void KPIByAge();

    void KPIByHireDate();
}
