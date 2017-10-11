BEGIN;
--
-- Create model Country
--
CREATE TABLE "lms_country" ("country_name" varchar(30) NOT NULL PRIMARY KEY);
--
-- Create model Department
--
CREATE TABLE "lms_department" ("id" serial NOT NULL PRIMARY KEY, "department_name" varchar(30) NOT NULL);
--
-- Create model Employee
--
CREATE TABLE "lms_employee" ("employee_id" varchar(30) NOT NULL PRIMARY KEY, "first_name" varchar(30) NOT NULL, "last_name" varchar(30) NOT NULL, "email" varchar(254) NOT NULL, "phone" varchar(13) NOT NULL, "salary" double precision NOT NULL, "hire_date" date NOT NULL, "department_id" integer NULL);
--
-- Create model Job
--
CREATE TABLE "lms_job" ("id" serial NOT NULL PRIMARY KEY, "job_name" varchar(30) NOT NULL, "min_salary" double precision NOT NULL, "max_salary" double precision NOT NULL);
--
-- Create model LeaveBalance
--
CREATE TABLE "lms_leavebalance" ("id" serial NOT NULL PRIMARY KEY, "taken_days" integer NOT NULL, "total_days" integer NULL, "employee_id" varchar(30) NOT NULL);
--
-- Create model LeaveRequest
--
CREATE TABLE "lms_leaverequest" ("id" serial NOT NULL PRIMARY KEY, "request_date" date NOT NULL, "start_date" date NOT NULL, "end_date" date NOT NULL, "status" varchar(30) NOT NULL, "employee_id" varchar(30) NOT NULL);
--
-- Create model LeaveType
--
CREATE TABLE "lms_leavetype" ("leave_type" varchar(30) NOT NULL PRIMARY KEY, "total_days" integer NULL);
--
-- Create model Location
--
CREATE TABLE "lms_location" ("id" serial NOT NULL PRIMARY KEY, "street_adress" text NOT NULL, "postal_code" integer NOT NULL, "city" varchar(30) NOT NULL, "state_province" varchar(30) NOT NULL, "country_id" varchar(30) NOT NULL);
--
-- Create model Region
--
CREATE TABLE "lms_region" ("region_name" varchar(30) NOT NULL PRIMARY KEY);
--
-- Add field leave_type to leaverequest
--
ALTER TABLE "lms_leaverequest" ADD COLUMN "leave_type_id" varchar(30) NOT NULL;
--
-- Add field leave_type to leavebalance
--
ALTER TABLE "lms_leavebalance" ADD COLUMN "leave_type_id" varchar(30) NOT NULL;
--
-- Add field job to employee
--
ALTER TABLE "lms_employee" ADD COLUMN "job_id" integer NULL;
--
-- Add field leaves to employee
--
--
-- Add field manager to employee
--
ALTER TABLE "lms_employee" ADD COLUMN "manager_id" varchar(30) NULL;
--
-- Add field department_manager to department
--
ALTER TABLE "lms_department" ADD COLUMN "department_manager_id" varchar(30) NULL;
--
-- Add field location to department
--
ALTER TABLE "lms_department" ADD COLUMN "location_id" integer NOT NULL;
--
-- Add field region to country
--
ALTER TABLE "lms_country" ADD COLUMN "region_id" varchar(30) NOT NULL;
CREATE INDEX "lms_country_country_name_3b411c0b_like" ON "lms_country" ("country_name" varchar_pattern_ops);
ALTER TABLE "lms_employee" ADD CONSTRAINT "lms_employee_department_id_d1b3203b_fk_lms_department_id" FOREIGN KEY ("department_id") REFERENCES "lms_department" ("id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_employee_employee_id_4dd0e66b_like" ON "lms_employee" ("employee_id" varchar_pattern_ops);
CREATE INDEX "lms_employee_department_id_d1b3203b" ON "lms_employee" ("department_id");
ALTER TABLE "lms_leavebalance" ADD CONSTRAINT "lms_leavebalance_employee_id_a02f0bde_fk_lms_emplo" FOREIGN KEY ("employee_id") REFERENCES "lms_employee" ("employee_id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_leavebalance_employee_id_a02f0bde" ON "lms_leavebalance" ("employee_id");
CREATE INDEX "lms_leavebalance_employee_id_a02f0bde_like" ON "lms_leavebalance" ("employee_id" varchar_pattern_ops);
ALTER TABLE "lms_leaverequest" ADD CONSTRAINT "lms_leaverequest_employee_id_8332d78e_fk_lms_emplo" FOREIGN KEY ("employee_id") REFERENCES "lms_employee" ("employee_id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_leaverequest_employee_id_8332d78e" ON "lms_leaverequest" ("employee_id");
CREATE INDEX "lms_leaverequest_employee_id_8332d78e_like" ON "lms_leaverequest" ("employee_id" varchar_pattern_ops);
CREATE INDEX "lms_leavetype_leave_type_7812c324_like" ON "lms_leavetype" ("leave_type" varchar_pattern_ops);
ALTER TABLE "lms_location" ADD CONSTRAINT "lms_location_country_id_3b069b14_fk_lms_country_country_name" FOREIGN KEY ("country_id") REFERENCES "lms_country" ("country_name") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_location_country_id_3b069b14" ON "lms_location" ("country_id");
CREATE INDEX "lms_location_country_id_3b069b14_like" ON "lms_location" ("country_id" varchar_pattern_ops);
CREATE INDEX "lms_region_region_name_dd8cc4b5_like" ON "lms_region" ("region_name" varchar_pattern_ops);
CREATE INDEX "lms_leaverequest_leave_type_id_e046b191" ON "lms_leaverequest" ("leave_type_id");
CREATE INDEX "lms_leaverequest_leave_type_id_e046b191_like" ON "lms_leaverequest" ("leave_type_id" varchar_pattern_ops);
ALTER TABLE "lms_leaverequest" ADD CONSTRAINT "lms_leaverequest_leave_type_id_e046b191_fk_lms_leave" FOREIGN KEY ("leave_type_id") REFERENCES "lms_leavetype" ("leave_type") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_leavebalance_leave_type_id_66f3bd2b" ON "lms_leavebalance" ("leave_type_id");
CREATE INDEX "lms_leavebalance_leave_type_id_66f3bd2b_like" ON "lms_leavebalance" ("leave_type_id" varchar_pattern_ops);
ALTER TABLE "lms_leavebalance" ADD CONSTRAINT "lms_leavebalance_leave_type_id_66f3bd2b_fk_lms_leave" FOREIGN KEY ("leave_type_id") REFERENCES "lms_leavetype" ("leave_type") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_employee_job_id_a88af6fe" ON "lms_employee" ("job_id");
ALTER TABLE "lms_employee" ADD CONSTRAINT "lms_employee_job_id_a88af6fe_fk_lms_job_id" FOREIGN KEY ("job_id") REFERENCES "lms_job" ("id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_employee_manager_id_d89b9c5e" ON "lms_employee" ("manager_id");
CREATE INDEX "lms_employee_manager_id_d89b9c5e_like" ON "lms_employee" ("manager_id" varchar_pattern_ops);
ALTER TABLE "lms_employee" ADD CONSTRAINT "lms_employee_manager_id_d89b9c5e_fk_lms_employee_employee_id" FOREIGN KEY ("manager_id") REFERENCES "lms_employee" ("employee_id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_department_department_manager_id_e686f924" ON "lms_department" ("department_manager_id");
CREATE INDEX "lms_department_department_manager_id_e686f924_like" ON "lms_department" ("department_manager_id" varchar_pattern_ops);
ALTER TABLE "lms_department" ADD CONSTRAINT "lms_department_department_manager_i_e686f924_fk_lms_emplo" FOREIGN KEY ("department_manager_id") REFERENCES "lms_employee" ("employee_id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_department_location_id_54bd25ce" ON "lms_department" ("location_id");
ALTER TABLE "lms_department" ADD CONSTRAINT "lms_department_location_id_54bd25ce_fk_lms_location_id" FOREIGN KEY ("location_id") REFERENCES "lms_location" ("id") DEFERRABLE INITIALLY DEFERRED;
CREATE INDEX "lms_country_region_id_2ebcdef4" ON "lms_country" ("region_id");
CREATE INDEX "lms_country_region_id_2ebcdef4_like" ON "lms_country" ("region_id" varchar_pattern_ops);
ALTER TABLE "lms_country" ADD CONSTRAINT "lms_country_region_id_2ebcdef4_fk_lms_region_region_name" FOREIGN KEY ("region_id") REFERENCES "lms_region" ("region_name") DEFERRABLE INITIALLY DEFERRED;
COMMIT;
