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

    public static void addEmployee() {
        Employee e = new Employee();
        e.setFirst_name(InputUtil.requiredName("Enter Employee First Name: ", 2, 20));
        e.setLast_name(InputUtil.requiredName("Enter Employee Last Name: ", 2, 10));
        e.setGender(InputUtil.requiredGender("Enter Employee Gender (m/male | f/female): "));
        e.setDate_of_birth(InputUtil.requiredDob("Enter Employee Date of Birth (yyyy-mm-dd): "));
        e.setEmail(InputUtil.requiredEmail("Enter Employee Email: "));
        e.setPhone_number(InputUtil.requiredPhone("Enter Employee Phone Number: "));
        e.setPosition(InputUtil.requiredPosition("Enter Employee Position: "));
        e.setSalary(InputUtil.requiredSalary("Enter Employee Salary: "));
        e.setHire_date(InputUtil.requiredHireDate("Enter Employee Hire Date (yyyy-mm-dd): "));
        e.setStatus(InputUtil.requiredStatus("Enter Employee Status (a/active | i/inactive): "));

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
        Integer code = Integer.valueOf(InputUtil.inputEmpIdRequired("Enter Employee ID: "));
        String firstName = InputUtil.inputNameOptional("Enter Employee First Name: ", 2, 20);
        String lastName = InputUtil.inputNameOptional("Enter Employee Last Name: ", 2, 10);
        String gender = InputUtil.inputGenderOptional("Enter Employee Gender (m/male | f/female): ");
        String date_of_birth = String.valueOf(InputUtil.inputDobOptional("Enter Employee Date of Birth (yyyy-mm-dd): "));
        String email = InputUtil.inputEmailOptional("Enter Employee Email: ");
        String phone_number = InputUtil.inputPhoneOptional("Enter Employee Phone number: ");
        String position = InputUtil.inputPositionOptional("Enter Employee Position: ");
        String salary = String.valueOf(InputUtil.inputSalaryOptional("Enter Employee Salary: "));
        String hire_date = String.valueOf(InputUtil.inputHireDateOptional("Enter Employee Hire Date (yyyy-mm-dd): "));
        String status = String.valueOf(InputUtil.inputStatusOptional("Enter Employee Status (a/active | i/inactive): "));

        Employee employee = new Employee();

        employee.setFirst_name(firstName);
        employee.setLast_name(lastName);
        employee.setGender(gender);
        employee.setDate_of_birth(LocalDate.parse(date_of_birth));
        employee.setEmail(email);
        employee.setPhone_number(phone_number);
        employee.setPosition(position);
        employee.setSalary(new BigDecimal(salary));
        employee.setHire_date(LocalDate.parse(hire_date));
        employee.setStatus(Boolean.valueOf(status));

        try {
            EmployeeService employeeService = new EmployeeServiceImpl();
            employeeService.updateEmployeeById(code, employee);

        } catch (NumberFormatException exception) {
            View.printHeader("Invalid Employee ID");
            View.printHeader(exception.getMessage());
        }
    }

}
