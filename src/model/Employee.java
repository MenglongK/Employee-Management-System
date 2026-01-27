package model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private int emp_id;
    private String first_name;
    private String last_name;
    private String gender;
    private LocalDate date_of_birth;
    private String email;
    private String phone_number;
    private String position;
    private BigDecimal salary;
    private LocalDate hire_date;
    private Boolean status;
}
