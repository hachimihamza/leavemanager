package lms;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hamza on 4/30/17.
 */

public class Employee implements Serializable {
    public static String ROOT = "http://192.168.1.23:8000/lms/";
    public String employeeID;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String role;

    public static String getROOT() {
        return ROOT;
    }

    public static void setROOT(String ROOT) {
        Employee.ROOT = ROOT;
    }

    public List<LeaveBalance> leaveBalances;

    public Employee(String employee_id) {
        this.employeeID = employee_id;
    }

    public String getLeaveHistoryURL() {
        return Employee.ROOT + employeeID + "/leave-history";
    }

    public String getLeaveBalanceURL() {
        return Employee.ROOT + employeeID + "/leave-balance";
    }

    public static String getRoot() {
        return ROOT;
    }

    public static void setRoot(String root) {
        Employee.ROOT = root;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employee_id) {
        this.employeeID = employee_id;
    }

    public List<LeaveBalance> getLeaveBalances() {
        return leaveBalances;
    }

    public void setLeaveBalances(List<LeaveBalance> leaveBalances) {
        this.leaveBalances = leaveBalances;
    }
}

