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

}
