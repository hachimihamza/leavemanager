from django.contrib import admin
from .models import Region, Country, Location
from .models import Job, Department, Employee
from .models import LeaveType, LeaveBalance

# Register your models here.
admin.site.register(Region)
admin.site.register(Country)
admin.site.register(Location)
admin.site.register(LeaveType)
admin.site.register(Job)
admin.site.register(Department)


class LeaveBalanceInline(admin.TabularInline):
	model = LeaveBalance

class EmployeeAdmin(admin.ModelAdmin):
	fieldsets = [
		('Basic Inforamtion',{
			'fields': ['employee_id', 'first_name', 'last_name', 'email', 'phone', 'password']
			}),
		('Extra Information',{
			'fields': ['salary', 'hire_date', 'job', 'department', 'manager'],
			})
	]
	inlines = [LeaveBalanceInline]

admin.site.register(Employee, EmployeeAdmin)