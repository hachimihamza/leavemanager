from django.conf.urls import url
from . import views

app_name = 'lms'

urlpatterns = [
	url(r'leave-types$', views.leave_types, name='leave_types'),
	url(r'employee-signin$', views.employee_signin, name='employee_signin'),
	url(r'request-leave$', views.request_leave, name='request_leave'),
	url(r'(?P<employee_id>\w*)/leave-balance$', views.leave_balance, name='leave_blance'),
	url(r'(?P<employee_id>\w*)/leave-history$', views.leave_history, name='leave_history'),
	url(r'(?P<employee_id>\w*)/basic-information$', views.basic_information, name='basic_information'),
	url(r'(?P<employee_id>\w*)/save-info$', views.save_info, name='save_info'),
	url(r'(?P<manager_id>\w*)/manager-requests$', views.manager_requests, name='manager_resquests'),
	url(r'(?P<drh_id>\w*)/drh-requests$', views.drh_requests, name='drh_resquests'),
	url(r'(?P<request_id>\w*)/accept-request$', views.accept_request, name='drh_resquests'),
	url(r'(?P<request_id>\w*)/refuse-request$', views.refuse_request, name='drh_resquests'),
]
