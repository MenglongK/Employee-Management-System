package dao;

import config.DatabaseConfig;
import model.Employee;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                INSERT INTO employee
                (first_name, last_name, gender, date_of_birth, email, phone_number, position, salary, hire_date, status)
                VALUES(?,?,?,?,?,?,?,?,?,?)
                """;
        Connection conn = DatabaseConfig.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getLast_name());
            ps.setString(3, employee.getGender());
            ps.setDate(4, Date.valueOf(employee.getDate_of_birth()));
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getPhone_number());
            ps.setString(7, employee.getPosition());
            ps.setBigDecimal(8, employee.getSalary());
            ps.setDate(9, Date.valueOf(employee.getHire_date()));
            ps.setBoolean(10, employee.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(int emp_id) {

    }

    @Override
    public List<Employee> getAllEmployees(int page, int pageSize) {
        List<Employee> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = """
                SELECT * FROM employee ORDER BY emp_id LIMIT ? OFFSET ?
                """;
        Connection conn = DatabaseConfig.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setFirst_name(rs.getString("first_name"));
                employee.setLast_name(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setPosition(String.valueOf(rs.getInt("position")));
                list.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Employee> searchEmployeeByPosition(String position, int page, int pageSize) {
        return List.of();
    }

    @Override
    public List<Employee> searchEmployeeBySalary(int salary, int page, int pageSize) {
        return List.of();
    }

    @Override
    public List<Employee> searchEmployeeByHireDate(String hire_date, int page, int pageSize) {
        return List.of();
    }

    @Override
    public List<Employee> topSalaryEmployees(int page, int pageSize) {
        return List.of();
    }
}
