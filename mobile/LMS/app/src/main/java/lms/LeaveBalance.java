package lms;

/**
 * Created by hamza on 4/30/17.
 */

public class LeaveBalance {
    public String leaveType;
    public int totalDays;
    public int takenDays;
    public int remainingDays;

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getTakenDays() {
        return takenDays;
    }

    public void setTakenDays(int takenDays) {
        this.takenDays = takenDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public int computeRemainingDays() {
        return totalDays - takenDays;
    }
}
