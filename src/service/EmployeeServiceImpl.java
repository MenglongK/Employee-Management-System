package service;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;
import util.InputUtil;
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
    public void updateEmployeeById(int code, Employee employee) {
        try {
            Employee foundEmployee = employeeDao.findById(code)
                    .orElseThrow(() -> new RuntimeException("Employee Not Found"));

            // IMPORTANT: check NEW input (employee), not foundEmployee
            if (employee.getFirst_name() != null && !employee.getFirst_name().isBlank())
                foundEmployee.setFirst_name(employee.getFirst_name());

            if (employee.getLast_name() != null && !employee.getLast_name().isBlank())
                foundEmployee.setLast_name(employee.getLast_name());

            if (employee.getGender() != null && !employee.getGender().isBlank())
                foundEmployee.setGender(employee.getGender());

            if (employee.getDate_of_birth() != null)
                foundEmployee.setDate_of_birth(employee.getDate_of_birth());

            if (employee.getEmail() != null && !employee.getEmail().isBlank())
                foundEmployee.setEmail(employee.getEmail());

            if (employee.getPhone_number() != null && !employee.getPhone_number().isBlank())
                foundEmployee.setPhone_number(employee.getPhone_number());

            if (employee.getPosition() != null && !employee.getPosition().isBlank())
                foundEmployee.setPosition(employee.getPosition());

            if (employee.getSalary() != null && employee.getSalary().compareTo(BigDecimal.ZERO) > 0)
                foundEmployee.setSalary(employee.getSalary());

            if (employee.getHire_date() != null)
                foundEmployee.setHire_date(employee.getHire_date());

            if (employee.getStatus() != null)
                foundEmployee.setStatus(employee.getStatus());

            int affectedRow = employeeDao.updateEmployee(code, foundEmployee);
            if (affectedRow < 1) View.printHeader("Update Employee Failed");
            else View.printHeader("Update Employee Success");

        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
    }

//    public void updateEmployeeById(int code, Employee employee) {
//        try {
//            Employee foundEmployee = employeeDao.findById(code).orElseThrow(() -> new RuntimeException("Employee Not Found"));
//            if (!foundEmployee.getFirst_name().isBlank())
//                foundEmployee.setFirst_name(employee.getFirst_name());
//            if (!foundEmployee.getLast_name().isBlank())
//                foundEmployee.setLast_name(employee.getLast_name());
//            if (!foundEmployee.getGender().isBlank())
//                foundEmployee.setGender(employee.getGender());
//            if (foundEmployee.getDate_of_birth() != null)
//                foundEmployee.setDate_of_birth(foundEmployee.getDate_of_birth());
//            if (!foundEmployee.getEmail().isBlank())
//                foundEmployee.setEmail(employee.getEmail());
//            if (!foundEmployee.getPhone_number().isBlank())
//                foundEmployee.setPhone_number(employee.getPhone_number());
//            if (foundEmployee.getSalary() != null)
//                foundEmployee.setSalary(foundEmployee.getSalary());
//            if (foundEmployee.getDate_of_birth() != null)
//                foundEmployee.setDate_of_birth(foundEmployee.getDate_of_birth());
//            if (foundEmployee.getStatus() != null)
//                foundEmployee.setStatus(foundEmployee.getStatus());
//            int affectedRow = employeeDao.updateEmployee(code, foundEmployee);
//            if (affectedRow < 1)
//                View.printHeader("Update Employee Failed");
//
//        } catch (SQLException e) {
//            View.printHeader(e.getMessage());
//        }
//    }

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

            // must be digits only
            if (!String.valueOf(emp_id).trim().matches("^[1-9]\\d*$")) {
                View.printHeader("Invalid employee ID. ID must be a positive number.");
                return;
            }

            if (employeeDao.findById(emp_id).isEmpty()) {
                View.printHeader("Employee Not Found");
                return;
            }

            employeeDao.deleteEmployee(emp_id);
            View.printHeader("Delete Employee Success");
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
