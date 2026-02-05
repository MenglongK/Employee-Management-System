package view;

import model.Employee;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import util.InputUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class View {

    public static final int pageSize = 5;

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
        table.addCell("2. Search Employee By Salary");
        table.addCell("3. Search Employee By Hire Date");
        table.addCell("0. Back To Main Menu");
        printText(table.render(), true);
    }

    public static void printEmployeeReportMenu() {
        Table table = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0, 30, 70);
        table.addCell("Employee Report", cellStyle);
        table.addCell("1. Top1 -> Top3 Highest Salary");
        table.addCell("2. Filter Employee By Age");
        table.addCell("3. KPI By Age");
        table.addCell("4. KPI By Hire Date");
        table.addCell("0. Back To Main Menu");
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
        InputUtil.pressEnter();
    }

    public static void listAllEmployees(List<Employee> employees) {

        int totalEmployees = employees.size();
        int totalPages = (int) Math.ceil((double) totalEmployees / pageSize);
        int currentPage = 1;

        while (true) {

            int startIdx = (currentPage - 1) * pageSize;
            int endIdx = Math.min(startIdx + pageSize, totalEmployees);

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

            for (int i = startIdx; i < endIdx; i++) {
                Employee e = employees.get(i);
                table.addCell(String.valueOf(e.getEmp_id()));
                table.addCell(e.getFirst_name());
                table.addCell(e.getLast_name());
                table.addCell(e.getGender());
                table.addCell(e.getDate_of_birth().toString() + " => " + Period.between(e.getDate_of_birth(), LocalDate.now()).getYears() + "years");
                table.addCell(e.getEmail());
                table.addCell(e.getPhone_number());
                table.addCell(e.getPosition());
                table.addCell(e.getSalary().toString());
                table.addCell(e.getHire_date().toString() + " => " + Period.between(e.getHire_date(), LocalDate.now()).getYears() + "years");
                table.addCell(e.getStatus().toString());
            }
            printText(table.render(), true);

            System.out.println("Page " + currentPage + " of " + totalPages);
            System.out.println("[n] Next | [p] Previous | [q] Quit | [g] Go to page");
            System.out.print("Enter Option: ");

            String input = InputUtil.scanner.nextLine().toLowerCase().trim();

            if (input.equals("n") && currentPage < totalPages) {
                currentPage++; // Go to the next page
            } else if (input.equals("p") && currentPage > 1) {
                currentPage--; // Go to the previous page
            } else if (input.equals("q")) {
                break; // Quit the pagination loop
            } else if (input.equals("g")) {
                // Ask the user to input the page number to go to
                System.out.print("Enter page number: ");
                int pageNumber = InputUtil.scanner.nextInt();
                InputUtil.scanner.nextLine(); // Consume the newline character
                if (pageNumber >= 1 && pageNumber <= totalPages) {
                    currentPage = pageNumber; // Go to the specified page
                } else {
                    System.out.println("Invalid page number. Try again.");
                }
            }
        }

        InputUtil.pressEnter();
    }


    public static void updateEmployee() {
        Employee employee = new Employee();
        EmployeeService employeeService = EmployeeServiceImpl.employeeService;
        Integer code;
        while (true) {
            code = Integer.valueOf(InputUtil.inputEmpIdRequired("Enter Employee ID: "));

            try {
                if (!employeeService.findById(code).isPresent()) {
                    printHeader("Employee Not Found");
                    continue;
                }
                break;

            } catch (Exception e) {
                printHeader(e.getMessage());
            }
        }

        String firstName = InputUtil.inputNameOptional("Enter Employee First Name: ", 2, 20);
        String lastName = InputUtil.inputNameOptional("Enter Employee Last Name: ", 2, 10);
        String gender = InputUtil.inputGenderOptional("Enter Employee Gender (m/male | f/female): ");
        LocalDate date_of_birth = InputUtil.inputDobOptional("Enter Employee Date of Birth (yyyy-mm-dd): ");
        String email = InputUtil.inputEmailOptional("Enter Employee Email: ");
        String phone_number = InputUtil.inputPhoneOptional("Enter Employee Phone number: ");
        String position = InputUtil.inputPositionOptional("Enter Employee Position: ");
        String salary = String.valueOf(InputUtil.inputSalaryOptional("Enter Employee Salary: "));
        LocalDate hire_date = InputUtil.inputHireDateOptional("Enter Employee Hire Date (yyyy-mm-dd): ");
        Boolean status = InputUtil.inputStatusOptional("Enter Employee Status (a/active | i/inactive): ");

        employee.setFirst_name(firstName);
        employee.setLast_name(lastName);
        employee.setGender(gender);
        employee.setDate_of_birth(date_of_birth);
        employee.setEmail(email);
        employee.setPhone_number(phone_number);
        employee.setPosition(position);
        employee.setSalary(new BigDecimal(salary));
        employee.setHire_date(hire_date);
        employee.setStatus(status);

        try {
            employeeService.updateEmployeeById(code, employee);
            InputUtil.pressEnter();
        } catch (NumberFormatException exception) {
            printHeader("Invalid Employee ID");
            printHeader(exception.getMessage());
        }
    }

    public static void printEmployeeReportMenuOption() {
        do {
            printEmployeeReportMenu();
            try {
                int opt = Integer.parseInt(InputUtil.getText("Enter Option: "));
                switch (opt) {
                    case 1 -> {
                        printHeader("Top1 -> Top3 Highest Salary");
                        topHighestSalary();
                    }
                    case 2 -> {
                        printHeader("Filter Employee By Age");
                        filterEmployeesByAge();
                    }
                    case 3 -> {
                        printHeader("KPI By Age (Age >=50years + 5%)");
                        KPIByAge();
                    }
                    case 4 -> {
                        printHeader("KPI By Hire Date (Work >10years + 5%)");
                        KPIByHireDate();
                    }
                    case 0 -> {
                        InputUtil.pressEnter();
                        return;
                    }
                    default -> printHeader("Invalid Option");
                }
            } catch (NumberFormatException e) {
                printHeader("Invalid Option");
            }
        } while (true);
    }

    public static void printSearchMenuOption() {
        do {
            printSearchMenu();
            try {
                int opt = Integer.parseInt(InputUtil.getText("Enter Option: "));
                switch (opt) {
                    case 1 -> {
                        printHeader("Search Employee By Position");
                        try {
                            String position = InputUtil.requiredPosition("Enter Position: ");
                            List<Employee> employees = EmployeeServiceImpl.employeeService.searchEmployeeByPosition(position);
                            if (employees.size() == 0) {
                                printHeader("Employee Not Found");
                                continue;
                            }
                            listAllEmployees(employees);
                        } catch (Exception ex) {
                            printHeader(ex.getMessage());
                        }

                    }
                    case 2 -> {
                        printHeader("Search Employee By Salary");
                        try {
                            BigDecimal salary = InputUtil.requiredSalary("Enter Salary: ");
                            List<Employee> employees = EmployeeServiceImpl.employeeService.searchEmployeeBySalary(salary);
                            if (employees.isEmpty()) {
                                printHeader("Employee Not Found");
                                continue;
                            }
                            listAllEmployees(employees);
                        } catch (Exception ex) {
                            printHeader(ex.getMessage());
                        }
                    }
                    case 3 -> {
                        printHeader("Search Employee By Hire Date");
                        try {
                            LocalDate hireDate = InputUtil.requiredHireDate("Enter Hire Date: ");
                            List<Employee> employees = EmployeeServiceImpl.employeeService.searchEmployeeByHireDate(hireDate);
                            if (employees.size() == 0) {
                                printHeader("Employee Not Found");
                                continue;
                            }
                            listAllEmployees(employees);
                        } catch (Exception e) {
                            printHeader(e.getMessage());
                        }
                    }
                    case 0 -> {
                        InputUtil.pressEnter();
                        return;
                    }
                    default -> printHeader("Invalid Option");
                }
            } catch (NumberFormatException e) {
                printHeader("Invalid Option");
            }
        } while (true);
    }

    public static void topHighestSalary() {
        List<Employee> highestSalary = EmployeeServiceImpl.employeeService.topSalaryEmployees();
        highestSalary.forEach(employee -> {
            employee.getEmp_id();
            employee.getFirst_name();
            employee.getLast_name();
            employee.getGender();
            employee.getDate_of_birth();
            employee.getEmail();
            employee.getPhone_number();
            employee.getPosition();
            employee.getSalary();
            employee.getHire_date();
            employee.getStatus();
        });
        listAllEmployees(highestSalary);
    }

    public static void filterEmployeesByAge() {
        List<Employee> filterEmployeeByAge = EmployeeServiceImpl.employeeService.filterEmployeeByAge();
        listAllEmployees(filterEmployeeByAge);
    }

    public static void KPIByAge() {
        List<Employee> KPIByAge = EmployeeServiceImpl.employeeService.KPIByAge();
        listAllEmployees(KPIByAge);
    }

    public static void KPIByHireDate() {
        List<Employee> KPIByHireDate = EmployeeServiceImpl.employeeService.KPIByHireDate();
        listAllEmployees(KPIByHireDate);
    }
}
