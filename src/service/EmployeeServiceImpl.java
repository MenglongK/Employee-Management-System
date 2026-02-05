package service;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;
import util.InputUtil;
import view.View;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    public final EmployeeDao employeeDao = new EmployeeDaoImpl();

    public static EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    public void addEmployee(Employee employee) {
        try {
            int affectedRow = employeeDao.addEmployee(employee);
            if (affectedRow < 1) View.printHeader("Insert Employee Failed");
            else View.printHeader("Employee Added Successfully");

        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
    }

    @Override
    public void updateEmployeeById(int code, Employee employee) {
        try {

            var opt = employeeDao.findById(code);

            if (!opt.isPresent()) {
                View.printHeader("Employee Not Found");
                return;
            }

            Employee foundEmployee = opt.get();

            // IMPORTANT: check NEW input (employee), not foundEmployee
            if (employee.getFirst_name() != null && !employee.getFirst_name().isBlank())
                foundEmployee.setFirst_name(employee.getFirst_name().trim());

            if (employee.getLast_name() != null && !employee.getLast_name().isBlank())
                foundEmployee.setLast_name(employee.getLast_name().trim());

            if (employee.getGender() != null && !employee.getGender().isBlank())
                foundEmployee.setGender(employee.getGender().trim());

            if (employee.getDate_of_birth() != null) foundEmployee.setDate_of_birth(employee.getDate_of_birth());

            if (employee.getEmail() != null && !employee.getEmail().isBlank())
                foundEmployee.setEmail(employee.getEmail().trim());

            if (employee.getPhone_number() != null && !employee.getPhone_number().isBlank())
                foundEmployee.setPhone_number(employee.getPhone_number().trim());

            if (employee.getPosition() != null && !employee.getPosition().isBlank())
                foundEmployee.setPosition(employee.getPosition().trim());

            if (employee.getSalary() != null && employee.getSalary().compareTo(BigDecimal.ZERO) > 0)
                foundEmployee.setSalary(employee.getSalary());

            if (employee.getHire_date() != null) foundEmployee.setHire_date(employee.getHire_date());

            if (employee.getStatus() != null) foundEmployee.setStatus(employee.getStatus());

            int affectedRow = employeeDao.updateEmployee(code, foundEmployee);
            if (affectedRow < 1) View.printHeader("Update Employee Failed");
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

            // must be digits only
            if (!String.valueOf(emp_id).trim().matches("^[1-9]\\d*$")) {
                View.printHeader("Invalid employee ID. ID must be a positive number.");
                return;
            }

            if (employeeDao.findById(emp_id).isEmpty()) {
                View.printHeader("Employee Not Found");
                return;
            }
            if (employeeDao.findById(emp_id).get().getStatus() == false) {
                View.printHeader("Employee Already Deleted");
                InputUtil.pressEnter();
                return;
            }

            employeeDao.deleteEmployee(emp_id);
            View.printHeader("Delete Employee Success");
            InputUtil.pressEnter();
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
        List<Employee> employees = employeeService.getAllEmployees();
        if (position == null || position.trim().isEmpty()) position = "";
        position = position.trim();
        String finalPosition = position;
        return employees.stream().filter(p -> p.getPosition().toLowerCase().contains(finalPosition.toLowerCase())).toList();
    }

    @Override
    public List<Employee> searchEmployeeBySalary(BigDecimal salary) {
        List<Employee> employees = employeeService.getAllEmployees();
        if (salary == null) salary = BigDecimal.ZERO;

        BigDecimal finalSalary = salary;
        return employees.stream().filter(p -> p.getSalary().compareTo(finalSalary) == 0).toList();
    }

    @Override
    public List<Employee> searchEmployeeByHireDate(LocalDate hire_date) {
        List<Employee> employees = employeeService.getAllEmployees();
        if (hire_date == null || hire_date.isAfter(LocalDate.now()))
            View.printHeader("Hire Date Not Found. Hire Date must be before now.");

        LocalDate finalHireDate = hire_date;
        return employees.stream().filter(p -> p.getHire_date().compareTo(finalHireDate) == 0).toList();
    }

    @Override
    public List<Employee> topSalaryEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> filterEmployeeByAge() {
        List<Employee> employees = employeeService.getAllEmployees();
        return employees.stream().sorted(Comparator.comparing(Employee::getDate_of_birth)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> KPIByAge() {
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee employee : employees) {
            BigDecimal employeeSalary = employee.getSalary();
            BigDecimal multiplyKPIByAge = employeeSalary.multiply(BigDecimal.valueOf(0.05));
            BigDecimal multiplyKPIByHireDate = employeeSalary.multiply(BigDecimal.valueOf(0.05));
            if (Period.between(employee.getDate_of_birth(), LocalDate.now()).getYears() >= 50 && Period.between(employee.getHire_date(), LocalDate.now()).getYears() > 10) {
                employee.setSalary(employeeSalary.add(multiplyKPIByAge).add(multiplyKPIByHireDate));
            } else if (Period.between(employee.getDate_of_birth(), LocalDate.now()).getYears() >= 50) {
                employee.setSalary(employeeSalary.add(multiplyKPIByAge));
            } else if (Period.between(employee.getHire_date(), LocalDate.now()).getYears() > 10) {
                employee.setSalary(employeeSalary.add(multiplyKPIByHireDate));
            }
        }
        return employees.stream().sorted(Comparator.comparing(Employee::getDate_of_birth)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> KPIByHireDate() {
        List<Employee> employees = employeeService.KPIByAge();
        return employees.stream().sorted(Comparator.comparing(Employee::getHire_date)).collect(Collectors.toList());
    }
}
