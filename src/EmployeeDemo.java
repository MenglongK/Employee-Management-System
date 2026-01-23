import util.InputUtil;
import view.View;

import java.util.Scanner;

public class EmployeeDemo {
    static void main(String[] args) {
        do {
            View.printMenu();
            System.out.print("Enter Option: ");

            try {
                int option = Integer.parseInt(InputUtil.scanner.nextLine());
                switch (option){
                    case 1->{
                        View.printHeader("Add New Employee");
                    }
                    case 2->{
                        View.printHeader("Update Employee By ID");
                    }
                    case 3->{
                        View.printHeader("Delete Employee By ID");
                    }
                    case 4->{
                        View.printHeader("Search Employee");
                    }
                    case 5->{
                        View.printHeader("List All Employees");
                    }
                    case 6->{
                        View.printHeader("Employee Report");
                    }
                    case 0-> {
                        System.out.println("Exit the program...!");
                        System.exit(0);
                    }
                    default ->  System.out.println("Invalid Option");
                }
            }catch (NumberFormatException e){
                System.out.println("Option Must be Number");
            }

        }while (true);
    }
}
