package service;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    public final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public static EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    public void updateEmployeeById(Employee employee) {

    }

    @Override
    public void deleteEmployeeById(int emp_id) {

    }

    @Override
    public List<Employee> getAllEmployees(int page, int pageSize) {
        return employeeDao.getAllEmployees(page, pageSize);
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
