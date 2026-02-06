package util;

import dao.EmployeeDao;
import service.EmployeeServiceImpl;
import view.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {
    public static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    public static void pressEnter() {
        System.out.print("⚡ Press ENTER to continue...");
        scanner.nextLine();
    }

    // ============== Optional Validation ================
    // ---------- BASIC ----------
    public static String getText(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    // ---------- emp_id ----------
    public static int inputEmpIdRequired(String label) {
        while (true) {
            String s = getText(label);
            if (s.length() == 0) {
                View.printHeader("Employee ID is Required");
                continue;
            }
            if (!s.matches("-?\\d+")) {
                View.printHeader("ID must be a number");
                continue;
            }
            int id = Integer.parseInt(s);
            if (id <= 0) {
                View.printHeader("Invalid ID. Must be greater than ZERO");
                continue;
            }
            return id;
        }
    }

    // ---------- first_name / last_name ----------
    // allows letters + space + apostrophe + hyphen. No double spaces.
    public static String inputNameOptional(String label, int min, int max) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null; // skip

            if (s.length() < min || s.length() > max) {
                System.out.printf("Name length must be %d-%d characters.%n", min, max);
                continue;
            }

            if (!s.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")) {
                System.out.println("Invalid name. Use letters, single spaces, ' or - only.");
                continue;
            }
            return s;
        }
    }

    // ---------- gender ----------
    // Accept: M/F or Male/Female (case-insensitive). Returns normalized value.
    public static String inputGenderOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            s = s.toLowerCase();
            if (s.equals("m") || s.equals("male")) return "Male";
            if (s.equals("f") || s.equals("female")) return "Female";

            System.out.println("Invalid gender. Use M/F or Male/Female.");
        }
    }

    // ---------- date_of_birth ----------
    // Must be in the past. (Optional)
    public static LocalDate inputDobOptional(String label) {
        while (true) {
            String s = getText(label).trim();
            if (s.isEmpty()) return null;
            try {
                LocalDate d = LocalDate.parse(s, DATE_FMT);
                if (!d.isBefore(LocalDate.now())) {
                    System.out.println("DOB must be in the past.");
                    continue;
                }
                return d;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }

    // ---------- email ----------
    public static String inputEmailOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            if (s.length() > 120) {
                System.out.println("Email too long (max 120).");
                continue;
            }

            // simple safe regex
            if (!s.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("Invalid email format.");
                continue;
            }
            return s;
        }
    }

    // ---------- phone_number ----------
    // Allows: 8-15 digits, optional leading +. (Optional)
    public static String inputPhoneOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            if (!s.matches("^\\+?\\d{8,15}$")) {
                System.out.println("Invalid phone. Use 8-15 digits, optional leading +.");
                continue;
            }
            return s;
        }
    }

    // ---------- position ----------
    public static String inputPositionOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            if (s.length() < 2 || s.length() > 60) {
                System.out.println("Position length must be 2-60 characters.");
                continue;
            }

            // letters/numbers + single spaces + - / ' allowed
            if (!s.matches("^[A-Za-z0-9]+([ '-][A-Za-z0-9]+)*$")) {
                System.out.println("Invalid position. Use letters/numbers and single spaces only.");
                continue;
            }
            return s;
        }
    }

    // ---------- salary ----------
    // BigDecimal > 0, max 2 decimals (Optional)
    public static BigDecimal inputSalaryOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            // numbers with optional .xx (up to 2 decimal)
            if (!s.matches("^\\d+(\\.\\d{1,2})?$")) {
                System.out.println("Invalid salary. Example: 5000 or 5000.25");
                continue;
            }

            BigDecimal sal = new BigDecimal(s);
            if (sal.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Salary must be > 0.");
                continue;
            }
            return sal;
        }
    }

    // ---------- hire_date ----------
    // Must NOT be in future (Optional)
    public static LocalDate inputHireDateOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            try {
                LocalDate d = LocalDate.parse(s, DATE_FMT);
                if (d.isAfter(LocalDate.now())) {
                    System.out.println("Hire date cannot be in the future.");
                    continue;
                }
                return d;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }

    // ---------- status ----------
    // Accept true/false, 1/0, active/inactive, y/n (Optional)
    public static Boolean inputStatusOptional(String label) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) return null;

            s = s.toLowerCase();
            if (s.equals("a") || s.equals("active"))
                return true;
            if (s.equals("i") || s.equals("inactive"))
                return false;

            System.out.println("Invalid status. Use true/false, 1/0, active/inactive, y/n.");
        }
    }

    // =================== Required Validation ====================
    public static String requiredText(String label, int min, int max) {
        while (true) {
            String s = getText(label);
            if (s.isBlank()) {
                System.out.println("This field is required.");
                continue;
            }
            if (s.length() < min || s.length() > max) {
                System.out.printf("Length must be %d-%d characters.%n", min, max);
                continue;
            }
            return s;
        }
    }

    // ---------- FIRST/LAST NAME (required) ----------
    public static String requiredName(String label, int min, int max) {
        while (true) {
            String s = requiredText(label, min, max);
            if (!s.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")) {
                System.out.println("Invalid name. Use letters, single spaces, ' or - only. " + min + "->" + max + ".");
                continue;
            }
            return s;
        }
    }

    // ---------- GENDER (required) ----------
    public static String requiredGender(String label) {
        while (true) {
            String s = requiredText(label, 1, 10).toLowerCase();
            if (s.equals("m") || s.equals("male")) return "Male";
            if (s.equals("f") || s.equals("female")) return "Female";
            System.out.println("Invalid gender. Use M/F or Male/Female.");
        }
    }

    // ---------- DOB (required: past) ----------
    public static LocalDate requiredDob(String label) {
        while (true) {
            String s = requiredText(label, 10, 10);
            try {
                LocalDate d = LocalDate.parse(s, DATE_FMT);
                if (!d.isBefore(LocalDate.now())) {
                    System.out.println("DOB must be in the past.");
                    continue;
                }
                return d;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }

    // ---------- EMAIL (required) ----------
    public static String requiredEmail(String label) {
        while (true) {
            String s = requiredText(label, 6, 120);
            if (!s.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
                System.out.println("Email must be a valid @gmail.com address.");
                continue;
            }
            return s;
        }
    }

    // ---------- PHONE (required) ----------
    public static String requiredPhone(String label) {
        while (true) {
            String s = requiredText(label, 8, 16);
            if (!s.matches("^\\+?\\d{8,15}$")) {
                System.out.println("Invalid phone. Use 8-15 digits, optional leading +.");
                continue;
            }
            return s;
        }
    }

    // ---------- POSITION (required) ----------
    public static String requiredPosition(String label) {
        while (true) {
            String s = requiredText(label, 2, 60);
            if (!s.matches("^[A-Za-z]+( [A-Za-z]+)*$")) {
                System.out.println("Invalid position. Use letters only (2->60 letters).");
                continue;
            }
            return s;
        }
    }

    // ---------- SALARY (required BigDecimal > 0, 2 decimals max) ----------
    public static BigDecimal requiredSalary(String label) {
        while (true) {
            String s = requiredText(label, 1, 30);
            if (!s.matches("^\\d+(\\.\\d{1,2})?$")) {
                System.out.println("Invalid salary. Example: 5000 or 5000.25");
                continue;
            }
            BigDecimal sal = new BigDecimal(s);
            if (sal.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Salary must be > 0.");
                continue;
            }
            return sal;
        }
    }

    // ---------- HIRE DATE (required: not future) ----------
    public static LocalDate requiredHireDate(String label) {
        while (true) {
            String s = requiredText(label, 10, 10);
            try {
                LocalDate d = LocalDate.parse(s, DATE_FMT);
                if (d.isAfter(LocalDate.now())) {
                    System.out.println("Hire date cannot be in the future.");
                    continue;
                }
                return d;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }

    // ---------- STATUS (required) ----------
    public static boolean requiredStatus(String label) {
        while (true) {
            String s = requiredText(label, 1, 20).toLowerCase();
            if (s.equals("a") || s.equals("active"))
                return true;
            if (s.equals("i") || s.equals("inactive"))
                return false;

            System.out.println("Invalid status. Use active/inactive");
        }
    }
}
