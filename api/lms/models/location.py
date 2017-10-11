from django.db import models


class Region(models.Model):
	region_name = models.CharField(max_length=30, primary_key=True)

	def __str__(self):
		return self.region_name

class Country(models.Model):
	country_name = models.CharField(max_length=30, primary_key=True)
	region = models.ForeignKey(Region, on_delete=models.CASCADE)

	def __str__(self):
		return self.country_name

class Location(models.Model):
	street_adress =  models.TextField()
	postal_code = models.IntegerField()
	city = models.CharField(max_length=30)
	state_province = models.CharField(max_length=30, blank=True)
	country = models.ForeignKey(Country, on_delete=models.CASCADE)

	def __str__(self):
		return '%s %d %s %s %s' % (self.street_adress, self.postal_code, self.city, self.state_province, self.country)