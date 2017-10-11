from django.db import models
from .location import Location
from .leave import LeaveType


class Job(models.Model):
	job_name = models.CharField(max_length=30)
	min_salary = models.FloatField()
	max_salary = models.FloatField()

	def __str__(self):
		return self.job_name

class Department(models.Model):
	department_name = models.CharField(max_length=30, primary_key=True)
	department_manager = models.ForeignKey('Employee', 
		related_name ='departments',
		related_query_name ='departments',
		blank=True,
		null=True,
		on_delete=models.SET_NULL)
	location = models.ForeignKey(Location)

	def __str__(self):
		return self.department_name


class Employee(models.Model):
	employee_id = models.CharField(max_length=30, primary_key=True)
	first_name = models.CharField(max_length=30)
	last_name = models.CharField(max_length=30)
	email = models.EmailField()
	phone = models.CharField(max_length=13)
	salary = models.FloatField()
	hire_date = models.DateField()
	job = models.ForeignKey(Job, null=True, on_delete=models.SET_NULL)
	department = models.ForeignKey(Department, on_delete=models.SET_NULL, null=True)
	manager = models.ForeignKey('self',
		blank=True,
		null=True,
		on_delete=models.SET_NULL)
	leaves = models.ManyToManyField(LeaveType, through='LeaveBalance')
	password = models.CharField(max_length=30, default=123); 

	def __str__(self):
		return self.first_name + ' ' + self.last_name



	