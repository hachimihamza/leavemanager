package lms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

/**
 * Created by hamza on 5/2/17.
 */

public class RequestLeave {
    private String employeeID;
    private String leaveType = "Annual";

    private Date requestDate;
    private Date startDate = null;
    private Date endDate = null;
    private String status = "Pending (Manager)";
    private long duration;


    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public RequestLeave() {
        Calendar calendar = Calendar.getInstance();
        int day =  calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar. YEAR);
        requestDate = new Date(year, month, day);
    }
    public String getRequestDate() {
        return dateToString(requestDate);
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getStartDate() {
        return dateToString(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return dateToString(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void computeDuration() {
        if( startDate != null && endDate != null) {
            long n = endDate.getTime() - startDate.getTime();
            duration = TimeUnit.DAYS.convert(n, TimeUnit.MILLISECONDS);
        }
    }
    public static String dateToString(Date date) {
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonth());
        String day = String.valueOf(date.getDate());
        return  year + "-" + month + "-" + day;
    }
}
