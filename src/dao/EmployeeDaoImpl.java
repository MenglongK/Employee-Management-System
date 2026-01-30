package dao;

import config.DatabaseConfig;
import model.Employee;
import view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public int addEmployee(Employee employee) {
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
        return 1;
    }

    @Override
    public int updateEmployee(Employee employee) {
        String sql = """
                    UPDATE employee
                    SET first_name = ?,
                    last_name = ?,
                    gender = ?,
                    date_of_birth = ?,
                    email = ?,
                    phone_number = ?,
                    position = ?,
                    salary = ?,
                    hire_date = ?,
                    status = ?
                    WHERE emp_id = ?
                """;
        Connection conn = DatabaseConfig.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, employee.getEmp_id());
            ps.setString(2, employee.getFirst_name());
            ps.setString(3, employee.getLast_name());
            ps.setString(4, employee.getGender());
            ps.setDate(5, Date.valueOf(employee.getDate_of_birth()));
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getPhone_number());
            ps.setString(8, employee.getPosition());
            ps.setBigDecimal(9, employee.getSalary());
            ps.setDate(10, Date.valueOf(employee.getHire_date()));
            ps.setBoolean(11, employee.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
        return 0;
    }

    @Override
    public Optional<Employee> findById(int emp_id) throws SQLException {
        String sql = """
                        SELECT * FROM employee WHERE emp_id = ?;
                """;
        Connection conn = DatabaseConfig.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, emp_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setFirst_name(rs.getString("first_name"));
                employee.setLast_name(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDate_of_birth(rs.getDate("date_of_birth").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone_number(rs.getString("phone_number"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setHire_date(rs.getDate("hire_date").toLocalDate());
                employee.setPosition(rs.getString("position"));
                employee.setStatus(rs.getBoolean("status"));
                return Optional.of(employee);
            }
        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void deleteEmployee(int emp_id) {
        String sql = """
                        DELETE FROM employee  WHERE emp_id = ?;
                """;
        Connection connection = DatabaseConfig.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, emp_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            View.printHeader(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
//        int offset = (page - 1) * pageSize;
        String sql = """
                SELECT * FROM employee ORDER BY emp_id
                """;
        Connection conn = DatabaseConfig.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, pageSize);
//            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setFirst_name(rs.getString("first_name"));
                employee.setLast_name(rs.getString("last_name"));
                employee.setGender(rs.getString("gender"));
                employee.setDate_of_birth(rs.getDate("date_of_birth").toLocalDate());
                employee.setEmail(rs.getString("email"));
                employee.setPhone_number(rs.getString("phone_number"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setHire_date(rs.getDate("hire_date").toLocalDate());
                employee.setPosition(rs.getString("position"));
                employee.setStatus(rs.getBoolean("status"));
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
