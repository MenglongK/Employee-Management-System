package view;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

public class View {

    public static void printText(String text, boolean isNewLine) {
        if (isNewLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }
    }

    public static void printHeader(String text) {
        Table table = new Table(1,BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell(text);
        printText(table.render(), true);
    }

    public static void printMenu() {
        Table table = new Table(1,BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0,30,70);
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
        Table table = new Table(1,BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0, 30, 70);
        table.addCell("Search Employee", cellStyle);
        table.addCell("1. Search Employee By Position");
        table.addCell("3. Search Employee By Salary");
        table.addCell("2. Search Employee By Hire Date");
        printText(table.render(), true);
    }

    public static void printEmployeeReportMenu() {
        Table table = new Table(1,BorderStyle.UNICODE_ROUND_BOX_WIDE);
        CellStyle cellStyle = new CellStyle();
        table.setColumnWidth(0, 30, 70);
        table.addCell("Employee Report", cellStyle);
        table.addCell("1. Top Highest Salary");
        table.addCell("2. Filter Employee By Age");
        table.addCell("3. KPI By Age");
        table.addCell("4. KPI By Hire Date");
        printText(table.render(), true);
    }
}
