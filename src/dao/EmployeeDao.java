package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDao {
    // add new employee
    void addEmployee(Employee employee);

    // update employee
    void updateEmployee(Employee employee);

    // delete employee by ID
    void deleteEmployee(int emp_id);

    // list all employee and show pagination
    List<Employee> getAllEmployees(int page, int pageSize);

    // search employee by position, salary, hire_date
    List<Employee> searchEmployeeByPosition(String position, int page, int pageSize);
    List<Employee> searchEmployeeBySalary(int salary, int page, int pageSize);
    List<Employee> searchEmployeeByHireDate(String hire_date, int page, int pageSize);

    // top salary
    List<Employee> topSalaryEmployees(int page, int pageSize);

    // KPI by Age, hire_date


}
