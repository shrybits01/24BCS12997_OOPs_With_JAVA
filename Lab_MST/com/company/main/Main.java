package com.company.main;

import com.company.employee.Employee;
import com.company.exception.EmployeeNotFoundException;
import com.company.exception.InvalidSalaryException;
import com.company.service.EmployeeService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EmployeeService service = new EmployeeService();

        try {

            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            if (salary < 10000) {
                throw new InvalidSalaryException("Salary must be at least 10000.");
            }

            Employee e = new Employee(id, name, dept, salary);

            service.addEmployee(e);

            System.out.println("\nAll Employees:");
            service.displayEmployees();

            System.out.print("\nEnter ID to search: ");
            int searchId = sc.nextInt();

            service.searchEmployee(searchId);

        } catch (InvalidSalaryException e) {
            System.out.println(e.getMessage());
        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}