use vetapp;
ALTER TABLE `appointment` DROP FOREIGN KEY `appointment_ibfk_1`;
ALTER TABLE `appointment` DROP FOREIGN KEY `appointment_ibfk_2`;
ALTER TABLE `appointment` DROP FOREIGN KEY `appointment_ibfk_3`;
ALTER TABLE `certifications` DROP FOREIGN KEY `certifications_ibfk_1`;
ALTER TABLE `examination` DROP FOREIGN KEY `examination_ibfk_1`;
ALTER TABLE `health_record` DROP FOREIGN KEY `health_record_ibfk_1`;
ALTER TABLE `health_record` DROP FOREIGN KEY `health_record_ibfk_2`;
ALTER TABLE `inventory_maintenance` DROP FOREIGN KEY `inventory_maintenance_ibfk_1`;
ALTER TABLE `inventory_maintenance` DROP FOREIGN KEY `inventory_maintenance_ibfk_2`;
ALTER TABLE `invoice` DROP FOREIGN KEY `invoice_ibfk_1`;
ALTER TABLE `invoice` DROP FOREIGN KEY `invoice_ibfk_2`;
ALTER TABLE `medication` DROP FOREIGN KEY `medication_ibfk_1`;
ALTER TABLE `pet` DROP FOREIGN KEY `pet_ibfk_1`;
ALTER TABLE `prescription` DROP FOREIGN KEY `prescription_ibfk_1`;
ALTER TABLE `prescription` DROP FOREIGN KEY `prescription_ibfk_2`;
ALTER TABLE `prescription` DROP FOREIGN KEY `prescription_ibfk_3`;
ALTER TABLE `specialties` DROP FOREIGN KEY `specialties_ibfk_1`;
ALTER TABLE `tech` DROP FOREIGN KEY `tech_ibfk_1`;
ALTER TABLE `tech_exams` DROP FOREIGN KEY `tech_exams_ibfk_1`;
ALTER TABLE `tech_exams` DROP FOREIGN KEY `tech_exams_ibfk_2`;
ALTER TABLE `treatment` DROP FOREIGN KEY `treatment_ibfk_1`;
ALTER TABLE `vet` DROP FOREIGN KEY `vet_ibfk_1`;
ALTER TABLE `vet_exams` DROP FOREIGN KEY `vet_exams_ibfk_1`;
ALTER TABLE `vet_exams` DROP FOREIGN KEY `vet_exams_ibfk_2`;
DROP TABLE IF EXISTS APPOINTMENT;
DROP TABLE IF EXISTS CERTIFICATIONS;
DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS EXAMINATION;
DROP TABLE IF EXISTS HEALTH_RECORD;
DROP TABLE IF EXISTS INVENTORY;
DROP TABLE IF EXISTS INVENTORY_MAINTENANCE;
DROP TABLE IF EXISTS INVOICE;
DROP TABLE IF EXISTS PET;
DROP TABLE IF EXISTS PRESCRIPTION;
DROP TABLE IF EXISTS SPECIALTIES;
DROP TABLE IF EXISTS STAFF;
DROP TABLE IF EXISTS TECH;
DROP TABLE IF EXISTS TECH_EXAMS;
DROP TABLE IF EXISTS TREATMENT;
DROP TABLE IF EXISTS VET;
DROP TABLE IF EXISTS VET_EXAMS;
DROP TABLE IF EXISTS MEDICATION;
