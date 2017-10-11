from django.shortcuts import get_object_or_404, get_list_or_404, render
from django.http import JsonResponse, HttpResponse
from django.core import serializers
from .models import Employee, LeaveBalance, LeaveType, LeaveRequest
from .models import Department
from django.views.decorators.csrf import csrf_exempt


# Create your views here.

def leave_types(request):
    leave_types = get_list_or_404(LeaveType)
    data = serializers.serialize("json", leave_types)
    return HttpResponse(data)

def leave_balance(request, employee_id):
    leaveblance_set = LeaveBalance.objects.filter(employee=employee_id)
    data = serializers.serialize("json", leaveblance_set)
    return HttpResponse(data, content_type='application/json')

def leave_history(request, employee_id):
    leavehistory_set = LeaveRequest.objects.filter(employee=employee_id)
    data = serializers.serialize("json", leavehistory_set)
    return HttpResponse(data, content_type='application/json')

def basic_information(request, employee_id):
    employee = get_list_or_404(Employee, employee_id=employee_id)
    data = serializers.serialize('json', employee)
    return HttpResponse(data, content_type='application/json')

@csrf_exempt
def save_info(request, employee_id):
    employee = Employee.objects.get(pk=employee_id)
    employee.first_name = request.POST['first_name']
    employee.last_name = request.POST['last_name']
    employee.email = request.POST['email']
    employee.phone = request.POST['phone']
    employee.save()
    return HttpResponse('success')
    
@csrf_exempt 
def employee_signin(request):
    response = "notExist"
    employee_id = request.POST["employee_id"]
    try:
        employee = Employee.objects.get(pk=request.POST["employee_id"])
        response = "employee"

        hasNemployee = employee.employee_set.count()

        if hasNemployee > 0 :
            response = "manager"

        isDrh = Department.objects.filter(department_name='RH', department_manager = employee).count()

        if isDrh > 0:
            response = "DRH"

    except Employee.DoesNotExist:
        response = "notExist"
    finally:
        return HttpResponse(response);

@csrf_exempt
def request_leave(request):
    leave_type = LeaveType.objects.get(leave_type=request.POST["leave_type"])
    request_leave = LeaveRequest(employee_id=request.POST["employee_id"],
        leave_type = leave_type,
        request_date = request.POST["request_date"],
        start_date = request.POST["start_date"],
        end_date = request.POST["end_date"],
        status = request.POST["status"]
        )
    request_leave.save()
    return HttpResponse("your request was received and is pending approval")

@csrf_exempt
def manager_requests(request, manager_id):
    manager = Employee.objects.get(pk=manager_id)
    requests = get_list_or_404(LeaveRequest, status="Pending (Manager)", employee__manager = manager)
    data = serializers.serialize("json", requests)
    return HttpResponse(data)

@csrf_exempt
def drh_requests(request, drh_id):
    requests = get_list_or_404(LeaveRequest, status="Pending (DHR)")
    data = serializers.serialize("json", requests)
    return HttpResponse(data)


def accept_request(request, request_id):
    leaveRequest = LeaveRequest.objects.get(pk=request_id)
    print(leaveRequest.leave_type)
    
    if leaveRequest.leave_type.leave_type == 'Sick':
        leaveRequest.status = "Approved"
        leaveRequest.save()
        leaveBalance = LeaveBalance.objects.get(employee=leaveRequest.employee, leave_type=leaveRequest.leave_type)
        duration = leaveRequest.end_date - leaveRequest.start_date
        leaveBalance.taken_days += duration.days
        leaveBalance.save()
    else:
        if leaveRequest.status == 'Pending (Manager)':
            leaveRequest.status = 'Pending (DHR)'
            leaveRequest.save() 
        else:
            leaveRequest.status = "Approved"
            leaveRequest.save()
            leaveBalance = LeaveBalance.objects.get(employee=leaveRequest.employee, leave_type=leaveRequest.leave_type)
            duration = leaveRequest.end_date - leaveRequest.start_date
            leaveBalance.taken_days += duration.days
            leaveBalance.total_days -= duration.days
            leaveBalance.save()

    return HttpResponse("sucess");

def refuse_request(request, request_id):
    leaveRequest = LeaveRequest.objects.get(pk=request_id)
    leaveRequest.status = "Rejected"
    leaveRequest.save()    
    return HttpResponse("sucess");
                                                                   