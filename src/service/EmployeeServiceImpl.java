package service;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;
import view.View;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    public final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public static EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    public void addEmployee(Employee employee) {
        try {
            int affectedRow = employeeDao.addEmployee(employee);
            if (affectedRow < 1) {
                View.printHeader("Insert Employee Failed");
            }
        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
    }

    @Override
    public void updateEmployeeById(Employee employee) {
        try {
            if (employee == null || employee.getEmp_id() <= 0) {
                View.printHeader("Employee is null");
                return;
            }

            BigDecimal salary = employee.getSalary();
            if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
                View.printHeader("Salary must be greater than 0");
            }

            int row = employeeDao.updateEmployee(employee);

            if (row == 0) View.printHeader("Update Employee Failed");
            else View.printHeader("Update Employee Success");
        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
    }

    @Override
    public Optional<Employee> findById(int empId) {
        try {
            if (empId <= 0) {
                View.printHeader("Invalid employee id");
                return Optional.empty();
            }
            return employeeDao.findById(empId);
        } catch (SQLException e) {
            View.printHeader(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void deleteEmployeeById(int emp_id) {
        try {
            employeeDao.deleteEmployee(emp_id);
        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            return employeeDao.getAllEmployees();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            throw new RuntimeException("SQL Error: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> searchEmployeeByPosition(String position) {
        return List.of();
    }

    @Override
    public List<Employee> searchEmployeeBySalary(BigDecimal salary) {
        return List.of();
    }

    @Override
    public List<Employee> searchEmployeeByHireDate(String hire_date) {
        return List.of();
    }

    @Override
    public List<Employee> topSalaryEmployees(int page, int pageSize) {
        return List.of();
    }

    @Override
    public void KPIByAge() {

    }

    @Override
    public void KPIByHireDate() {

    }
}
