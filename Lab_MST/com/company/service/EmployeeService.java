package com.company.service;

import com.company.employee.Employee;
import com.company.exception.EmployeeNotFoundException;

import java.io.*;

public class EmployeeService {

    private static final String FILE_NAME = "employees.txt";

    public void addEmployee(Employee e) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            bw.write(e.getEmpId() + "," + e.getName() + "," + e.getDepartment() + "," + e.getSalary());
            bw.newLine();

        } catch (IOException ex) {
            System.out.println("Error writing to file.");
        }
    }

    public void displayEmployees() {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                System.out.println("ID: " + data[0] +
                        " Name: " + data[1] +
                        " Department: " + data[2] +
                        " Salary: " + data[3]);
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public void searchEmployee(int empId) throws EmployeeNotFoundException {

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (Integer.parseInt(data[0]) == empId) {

                    System.out.println("Employee Found:");
                    System.out.println("ID: " + data[0]);
                    System.out.println("Name: " + data[1]);
                    System.out.println("Department: " + data[2]);
                    System.out.println("Salary: " + data[3]);

                    found = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        if (!found) {
            throw new EmployeeNotFoundException("Employee with ID " + empId + " not found.");
        }
    }
}
