package dao;

import model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    // add new employee
    int addEmployee(Employee employee) throws SQLException;

    // update employee
    int updateEmployee(int code ,Employee employee) throws SQLException;

    Optional<Employee> findById(int emp_id) throws SQLException;

    // delete employee by ID
    void deleteEmployee(int emp_id) throws SQLException;

    // list all employee and show pagination
    List<Employee> getAllEmployees() throws SQLException;

    // search employee by position, salary, hire_date
    List<Employee> searchEmployeeByPosition(String position, int page, int pageSize) throws SQLException;
    List<Employee> searchEmployeeBySalary(int salary, int page, int pageSize) throws SQLException;
    List<Employee> searchEmployeeByHireDate(String hire_date, int page, int pageSize) throws SQLException;

    // top salary
    List<Employee> topSalaryEmployees(int page, int pageSize) throws SQLException;

    // KPI by Age, hire_date


}
