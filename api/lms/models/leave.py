from django.db import models


class LeaveType(models.Model):
	leave_type = models.CharField(max_length=30, primary_key=True)
	total_days = models.IntegerField(blank=True, null=True)

	def __str__(self):
		return self.leave_type

class LeaveBalance(models.Model):
	leave_type = models.ForeignKey(LeaveType, on_delete=models.CASCADE)
	employee = models.ForeignKey('Employee', on_delete=models.CASCADE)
	taken_days = models.IntegerField()
	total_days = models.IntegerField(null=True, blank=True)

class LeaveRequest(models.Model):
	employee = models.ForeignKey('Employee', on_delete=models.CASCADE)
	leave_type = models.ForeignKey(LeaveType, on_delete=models.CASCADE)
	request_date = models.DateField()
	start_date = models.DateField()
	end_date = models.DateField()
	status = models.CharField(max_length=30)
