import config.DatabaseConfig;
import model.Employee;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import util.InputUtil;
import view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDemo {

    static void main(String[] args) {
        DatabaseConfig.init();

        do {
            View.printMenu();
            System.out.print("Enter Option: ");
            try {
                int option = Integer.parseInt(InputUtil.scanner.nextLine());
                switch (option) {
                    case 1 -> {
                        View.printHeader("Add New Employee");
                        try {
                            View.addEmployee();
                            View.printHeader("Employee Added Successfully");
                        } catch (RuntimeException e) {
                            View.printHeader(e.getMessage());
                        }
                    }
                    case 2 -> {
                        View.printHeader("Update Employee By ID");
                        View.updateEmployee();
                    }
                    case 3 -> {
                        View.printHeader("Delete Employee By ID");
                        try {
                            View.printText("Enter Employee ID: ", false);
                            int id = Integer.parseInt(InputUtil.scanner.nextLine());
                            EmployeeService service = new EmployeeServiceImpl();
                            service.deleteEmployeeById(id);
                        } catch (RuntimeException e) {
                            View.printHeader(e.getMessage());
                        }
                    }
                    case 4 -> {
                        View.printSearchMenu();
                    }
                    case 5 -> {
                        View.printHeader("List All Employees");
                        try {
                            List<Employee> employees = EmployeeServiceImpl.employeeService.getAllEmployees();
                            View.listAllEmployees(employees);
                        } catch (RuntimeException e) {
                            View.printHeader(e.getMessage());
                        }
                    }
                    case 6 -> {
                        View.printHeader("Employee Report");
                    }
                    case 0 -> {
                        System.out.println("Exit the program...!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Option");
                }
            } catch (NumberFormatException e) {
                System.out.println("Option Must be Number");
            }

        } while (true);
    }
}
