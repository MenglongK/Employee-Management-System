package view;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import util.InputUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class View {

    public static int offset(int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        return (page - 1) * size;
    }

    public static void printText(String text, boolean isNewLine) {
        if (isNewLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }
    }

    public static void printHeader(String text) {
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell(text);
        printText(table.render(), true);
    }

    public static void printMenu() {
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0, 30, 70);
        table.addCell("Employee Management System", cellStyle);
        table.addCell("1. Add New Employee");
        table.addCell("2. Update Employee By ID");
        table.addCell("3. Delete Employee By ID");
        table.addCell("4. Search Employee");
        table.addCell("5. List All Employees");
        table.addCell("6. Employee Report");
        table.addCell("0. Exit The Program");
        printText(table.render(), true);
    }

    public static void printSearchMenu() {
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0, 30, 70);
        table.addCell("Search Employee", cellStyle);
        table.addCell("1. Search Employee By Position");
        table.addCell("3. Search Employee By Salary");
        table.addCell("2. Search Employee By Hire Date");
        printText(table.render(), true);
    }

    public static void printEmployeeReportMenu() {
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0, 30, 70);
        table.addCell("Employee Report", cellStyle);
        table.addCell("1. Top Highest Salary");
        table.addCell("2. Filter Employee By Age");
        table.addCell("3. KPI By Age");
        table.addCell("4. KPI By Hire Date");
        printText(table.render(), true);
    }

    public static void add() {
        Employee e = new Employee();
        System.out.print("Enter Employee First Name: ");
        e.setFirst_name(InputUtil.scanner.nextLine());
        System.out.print("Enter Employee Last Name: ");
        e.setLast_name(InputUtil.scanner.nextLine());
        System.out.print("Enter Employee Gender: ");
        e.setGender(InputUtil.scanner.nextLine());
        System.out.print("Enter Employee Date of Birth (yyyy-mm-dd): ");
        try {
            e.setDate_of_birth(LocalDate.parse(InputUtil.scanner.nextLine()));
        } catch (RuntimeException exception) {
            View.printHeader(exception.getMessage());
        }
        System.out.print("Enter Employee Email: ");
        e.setEmail(InputUtil.scanner.nextLine());
        System.out.print("Enter Employee Phone number: ");
        e.setPhone_number(InputUtil.scanner.nextLine());
        System.out.print("Enter Employee Position: ");
        e.setPosition(InputUtil.scanner.nextLine());
        System.out.print("Enter Employee Salary: ");
        try {
            BigDecimal salary = new BigDecimal(InputUtil.scanner.nextLine());
            e.setSalary(salary);
        } catch (RuntimeException exception) {
            View.printHeader(exception.getMessage());
        }
        System.out.print("Enter Employee Hire Date (yyyy-mm-dd): ");
        try {
            e.setHire_date(LocalDate.parse(InputUtil.scanner.nextLine()));
        } catch (RuntimeException exception) {
            View.printHeader(exception.getMessage());
        }
        System.out.print("Enter Employee Status (true/false): ");
        try {
            e.setStatus(Boolean.valueOf(InputUtil.scanner.nextLine()));
        } catch (RuntimeException exception) {
            View.printHeader(exception.getMessage());
        }

        EmployeeServiceImpl.employeeService.addEmployee(e);
    }

    public static void listAllEmployees(List<Employee> employees) {
        Table table = new Table(11, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        table.addCell("ID");
        table.addCell("First Name");
        table.addCell("Last Name");
        table.addCell("Gender");
        table.addCell("Date of Birth");
        table.addCell("Email");
        table.addCell("Phone number");
        table.addCell("Position");
        table.addCell("Salary");
        table.addCell("Hire Date");
        table.addCell("Status");

        for (Employee e : employees) {
            table.addCell(String.valueOf(e.getEmp_id()));
            table.addCell(e.getFirst_name());
            table.addCell(e.getLast_name());
            table.addCell(e.getGender());
            table.addCell(e.getDate_of_birth().toString());
            table.addCell(e.getEmail());
            table.addCell(e.getPhone_number());
            table.addCell(e.getPosition());
            table.addCell(e.getSalary().toString());
            table.addCell(e.getHire_date().toString());
            table.addCell(e.getStatus().toString());
        }
        printText(table.render(), true);
    }

    public static void updateEmployee() {

        EmployeeDao employeeDao = new EmployeeDaoImpl();

        Employee e = new Employee();

        System.out.print("Enter Employee ID to update: ");
        e.setEmp_id(Integer.parseInt(InputUtil.scanner.nextLine()));

        System.out.print("First Name: ");
        e.setFirst_name(InputUtil.scanner.nextLine());

        System.out.print("Last Name: ");
        e.setLast_name(InputUtil.scanner.nextLine());

        System.out.print("Gender: ");
        e.setGender(InputUtil.scanner.nextLine());

        System.out.print("Date of Birth (yyyy-mm-dd): ");
        e.setDate_of_birth(LocalDate.parse(InputUtil.scanner.nextLine()));

        System.out.print("Email: ");
        e.setEmail(InputUtil.scanner.nextLine());

        System.out.print("Phone: ");
        e.setPhone_number(InputUtil.scanner.nextLine());

        System.out.print("Position: ");
        e.setPosition(InputUtil.scanner.nextLine());

        System.out.print("Salary: ");
        e.setSalary(BigDecimal.valueOf(Double.parseDouble(InputUtil.scanner.nextLine())));

        System.out.print("Hired Date (yyyy-mm-dd): ");
        e.setHire_date(LocalDate.parse(InputUtil.scanner.nextLine()));

        System.out.print("Status: ");
        e.setStatus(Boolean.valueOf(InputUtil.scanner.nextLine()));

        try {
            employeeDao.updateEmployee(e);
        } catch (SQLException ex) {
            View.printHeader(ex.getMessage());
        }

        System.out.println("✅ Employee updated successfully");
    }

}
