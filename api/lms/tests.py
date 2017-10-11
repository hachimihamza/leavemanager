from django.test import TestCase
from django.utils import timezone
from .models import *
from .urls import test
# Create your tests here.

def setup_db():
    r = Region.objects.create(region_name='Afrique')
    c = Country.objects.create(country_name='Morocco', region=r)
    l = Location.objects.create(street_adress="20 Madinat Al Irfane", postal_code=15000, country=c)

    software_engineer = Job.objects.create(job_name = "Software Engineer", min_salary =10000, max_salary = 15000)

    drh = Employee.objects.create(employee_id='S123456',first_name='Said',last_name='El farkh',email='said@gmail.com',phone_number='+212657899048',salary=40000,hire_date=timezone.now(),job=software_engineer)


class EmployeeMethodTests(TestCase):

    def test_set_leaves(self):
        setup_db()
        d = Employee.objects.get(employee_id='S123456')
        d.leaves.all()
