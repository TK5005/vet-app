use vetapp;
-- Create Tables with Primary Keys
CREATE TABLE APPOINTMENT(
        apptNo INT NOT NULL AUTO_INCREMENT,
        clientID INT NOT NULL,
        petID INT NOT NULL,
        staffID INT,
        start_time DATETIME NOT NULL,
        checkin_time DATETIME,
        description VARCHAR(1000),
        primary key (apptNo));

CREATE TABLE INVENTORY(
        itemID INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(50) NOT NULL,
        manufacturer VARCHAR(50) NOT NULL,
        type VARCHAR(15) NOT NULL,
        quantity INT NOT NULL,
        reorderLevel INT,
        reorderQuantity INT,
        wholesaleCost FLOAT,
        retailCost FLOAT,
        primary key (itemID));

CREATE TABLE STAFF(
        empID INT NOT NULL AUTO_INCREMENT,
        firstName VARCHAR(50) NOT NULL,
        lastName VARCHAR(50) NOT NULL,
        sex   CHAR(1),
        dob   DATE NOT NULL,
        ssn   CHAR(10) NOT NULL,
        phone VARCHAR(15) NOT NULL,
        street VARCHAR(50) NOT NULL,
        city VARCHAR(50) NOT NULL,
        state VARCHAR(30) NOT NULL,
        zip VARCHAR(10) NOT NULL,
        primary key (empID));

CREATE TABLE CLIENT(
        clientID INT NOT NULL AUTO_INCREMENT,
        firstName VARCHAR(50) NOT NULL,
        lastName VARCHAR(50) NOT NULL,
        phone VARCHAR(15) NOT NULL,
        email VARCHAR(50) NOT NULL,
        street VARCHAR(50) NOT NULL,
        city VARCHAR(50) NOT NULL,
        state VARCHAR(30) NOT NULL,
        zip VARCHAR(10) NOT NULL,
        primary key (clientID));

CREATE TABLE MEDICATION(
        itemID INT NOT NULL,
        interactions VARCHAR(250),
        dosage VARCHAR(20),
        primary key (itemID));

CREATE TABLE VET(
        empID INT NOT NULL,
        licenseNo VARCHAR(30) NOT NULL,
        primary key (empID));

CREATE TABLE TECH(
        empID INT NOT NULL,
        certNumber VARCHAR(30),
        primary key (empID));

CREATE TABLE PET(
        petID INT NOT NULL AUTO_INCREMENT,
        ownerID INT NOT NULL,
        name VARCHAR(50) NOT NULL,
        sex char(1) NOT NULL,
        birthdate DATE,
        species VARCHAR(30),
        breed VARCHAR(30),
        color VARCHAR(30),
        weight int,
        microchipNumber VARCHAR(50),
        rabiesTag VARCHAR(50),
        primary key(petID));

CREATE TABLE SPECIALTIES(
        empID INT NOT NULL,
        name VARCHAR(30) NOT NULL,
        primary key (empID, name));

CREATE TABLE CERTIFICATIONS(
        empID INT NOT NULL,
        name VARCHAR(30) NOT NULL,
        primary key (empID, name));

CREATE TABLE VET_EXAMS(
        empID INT NOT NULL,
        examID INT NOT NULL,
        primary key (empID, examID));

CREATE TABLE TECH_EXAMS(
        empID INT NOT NULL,
        examID INT NOT NULL,
        primary key (empID, examID));

CREATE TABLE EXAMINATION(
        examID INT NOT NULL AUTO_INCREMENT,
        petID INT NOT NULL,
        exam_datetime DATETIME NOT NULL,
        description VARCHAR(1000),
        vitals VARCHAR(500),
        weight DECIMAL,
        location VARCHAR(15) NOT NULL,
        primary key (examID));

CREATE TABLE PRESCRIPTION(
        empID INT NOT NULL,
        medID INT NOT NULL,
        trmntID INT NOT NULL,
        primary key (empID, medID, trmntID));

CREATE TABLE TREATMENT(
        trmntID INT NOT NULL AUTO_INCREMENT,
        examID INT NOT NULL,
        medID INT,
        type VARCHAR(15) NOT NULL,
        startDate DATE NOT NULL,
        endDate DATE,
        directions VARCHAR(1000),
        primary key (trmntID));

CREATE TABLE INVOICE(
        invoiceNo INT NOT NULL AUTO_INCREMENT,
        examID INT NOT NULL,
        custID INT NOT NULL,
        amtDue FLOAT NOT NULL,
        status VARCHAR(15) NOT NULL,
        invoiceDate DATE NOT NULL,
        primary key (invoiceNo));

-- Add Foreign Key Constraints

ALTER TABLE APPOINTMENT ADD CONSTRAINT FOREIGN KEY (clientID) REFERENCES CLIENT (clientID);
ALTER TABLE APPOINTMENT ADD CONSTRAINT FOREIGN KEY (petID) REFERENCES PET (petID);
ALTER TABLE APPOINTMENT ADD CONSTRAINT FOREIGN KEY (staffID) REFERENCES STAFF (empID);

ALTER TABLE MEDICATION ADD CONSTRAINT FOREIGN KEY (itemID) REFERENCES INVENTORY (itemID);

ALTER TABLE VET ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES STAFF (empID);

ALTER TABLE TECH ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES STAFF (empID);

ALTER TABLE PET ADD CONSTRAINT FOREIGN KEY (ownerID) REFERENCES CLIENT (clientID);

ALTER TABLE SPECIALTIES ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES VET (empID);
ALTER TABLE CERTIFICATIONS ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES TECH(empID);
ALTER TABLE VET_EXAMS ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES VET (empID);
ALTER TABLE VET_EXAMS ADD CONSTRAINT FOREIGN KEY (examID) REFERENCES EXAMINATION(examID);
ALTER TABLE TECH_EXAMS ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES TECH (empID);
ALTER TABLE TECH_EXAMS ADD CONSTRAINT FOREIGN KEY (examID) REFERENCES EXAMINATION (examID);
ALTER TABLE EXAMINATION ADD CONSTRAINT FOREIGN KEY (petID) REFERENCES PET (petID);
ALTER TABLE PRESCRIPTION ADD CONSTRAINT FOREIGN KEY (empID) REFERENCES VET (empID);
ALTER TABLE PRESCRIPTION ADD CONSTRAINT FOREIGN KEY (medID) REFERENCES MEDICATION (itemID);
ALTER TABLE PRESCRIPTION ADD CONSTRAINT FOREIGN KEY (trmntID) REFERENCES TREATMENT (trmntID);
ALTER TABLE TREATMENT ADD CONSTRAINT FOREIGN KEY (examID) REFERENCES EXAMINATION (examID);
ALTER TABLE TREATMENT ADD CONSTRAINT FOREIGN KEY (medID) REFERENCES MEDICATION(itemID);
ALTER TABLE INVOICE ADD CONSTRAINT FOREIGN KEY (examID) REFERENCES EXAMINATION (examID);
ALTER TABLE INVOICE ADD CONSTRAINT FOREIGN KEY (custID) REFERENCES CLIENT (clientID);

-- Insert Client Data

insert into CLIENT (firstName,lastName,phone,email,street,city,state,zip)
values('Kevin','Meads','555-555-5555','kmeads@test.com','123 Fake st','Perryville','MD','12345');

insert into CLIENT (firstName,lastName,phone,email,street,city,state,zip)
values('Kevin','Seldomridge','555-222-3333','kseldomridge@test.com','123 evergreen st','baltimore','MD','35352');

insert into CLIENT (firstName,lastName,phone,email,street,city,state,zip)
values('Vien','Vuong','555-566-7777','vvuong@test.com','123 elm st','York','PA','42424');

insert into CLIENT (firstName,lastName,phone,email,street,city,state,zip)
values('Steve','Hayes','555-888-3434','shayes@test.com','123 crayon pl','Perry Hall','MD','24242');

insert into CLIENT (firstName,lastName,phone,email,street,city,state,zip)
values('Dan','Rodriguez','242-222-4444','drodriguez@test.com','123 shamrock st','bel air','MD','24646');

-- Insert Pet Data

insert into PET (ownerID,name,sex,birthdate,species,breed,color, weight, microchipNumber,rabiesTag)
values('1','Muffin','F','2015-01-01','Dog','Lab','Black',75,123123123,456456456);

insert into PET (ownerID,name,sex,birthdate,species,breed,color, weight, microchipNumber,rabiesTag)
values('2','Bluey','F','2018-03-05','Dog','Blue Heeler','Blue',25,092834092834,92734923986);

insert into PET (ownerID,name,sex,birthdate,species,breed,color, weight, microchipNumber,rabiesTag)
values('3','Bingo','F','2020-08-05','Dog','Blue Heeler','Orange',15,5671641789,78945809345089);

insert into PET (ownerID,name,sex,birthdate,species,breed,color, weight, microchipNumber,rabiesTag)
values('4','Bandit','F','2010-09-05','Dog','Blue Heeler','Blue',50,892389023489,12348923984);

insert into PET (ownerID,name,sex,birthdate,species,breed,color, weight, microchipNumber,rabiesTag)
values('5','Chili','F','2009-04-07','Dog','Blue Heeler','Orange',40,12398590921354,8912892498890);

-- Insert Staff data
insert into STAFF (firstName,lastName,sex,dob,ssn,phone,street,city,state,zip)
values('Jenny','McCann','F','1988-07-01','123456789','555-999-1212','321 Shamrock Rd','Bel Air', 'MD', 21014);

insert into STAFF (firstName,lastName,sex,dob,ssn,phone,street,city,state,zip)
values('Danielle','Cherry','F','1990-10-01','666554444','555-999-1234','876 Cherry Rd','Howell', 'NJ', 085223);

-- Insert Vet/Tech data
insert into VET(empID,licenseNo)
values('1','VC193990230909');

insert into TECH(empID,certNumber)
values('2','VT123403782783592');

-- Insert Examination data
insert into EXAMINATION (petID,exam_datetime,description,vitals,weight,location)
values('1','2024-04-15 08:30:00','Yearly checkup everything looks great',
    'BP: 120/80 Temp: 100', 35, 'Exam 1');

insert into EXAMINATION (petID,exam_datetime,description,vitals,weight,location)
values('2','2024-04-16 08:45:00','Yearly checkup everything looks great',
    'BP: 120/87 Temp: 101', 40, 'Exam 2');

insert into EXAMINATION (petID,exam_datetime,description,vitals,weight,location)
values('3','2024-03-15 08:30:00','Yearly checkup everything looks great',
    'BP: 121/82 Temp: 100', 45, 'Exam 1');

insert into EXAMINATION (petID,exam_datetime,description,vitals,weight,location)
values('4','2024-04-10 12:30:00','Yearly checkup everything looks great',
    'BP: 128/82 Temp: 99', 32, 'Exam 4');

insert into EXAMINATION (petID,exam_datetime,description,vitals,weight,location)
values('5','2024-02-01 08:30:00','Sick Visit, possible fever',
    'BP: 121/87 Temp: 105', 50, 'Exam 4');

-- Insert Vet_Exam / Tech_Exam Info
insert into VET_EXAMS(empID,examID) values ('1','1');
insert into TECH_EXAMS(empID,examID) values ('2','1');
insert into VET_EXAMS(empID,examID) values ('1','2');
insert into TECH_EXAMS(empID,examID) values ('2','2');
insert into VET_EXAMS(empID,examID) values ('1','3');
insert into TECH_EXAMS(empID,examID) values ('2','3');
insert into VET_EXAMS(empID,examID) values ('1','4');
insert into TECH_EXAMS(empID,examID) values ('2','4');
insert into VET_EXAMS(empID,examID) values ('1','5');
insert into TECH_EXAMS(empID,examID) values ('2','5');

-- Insert Inventory Data
insert into INVENTORY (name, manufacturer, type, quantity,reorderLevel,reorderQuantity,wholesaleCost,retailCost)
values('Vaccine A','ABC Pharma','MEDICATION',100,50,100,50.00,75.00);

insert into INVENTORY (name, manufacturer, type, quantity,reorderLevel,reorderQuantity,wholesaleCost,retailCost)
values('Vaccine B','DEF Pharma','MEDICATION',100,50,100,50.00,75.00);

insert into INVENTORY (name, manufacturer, type, quantity,reorderLevel,reorderQuantity,wholesaleCost,retailCost)
values('Vaccine C','GHI Pharma','MEDICATION',100,50,100,50.00,75.00);

insert into INVENTORY (name, manufacturer, type, quantity,reorderLevel,reorderQuantity,wholesaleCost,retailCost)
values('Vaccine D','JKL Pharma','MEDICATION',100,50,100,50.00,75.00);

insert into INVENTORY (name, manufacturer, type, quantity,reorderLevel,reorderQuantity,wholesaleCost,retailCost)
values('Vaccine E','MNO Pharma','MEDICATION',100,50,100,50.00,75.00);

insert into INVENTORY (name, manufacturer, type, quantity,reorderLevel,reorderQuantity,wholesaleCost,retailCost)
values('4x4 Gauze','CVS','MEDICAL',100,50,100,50.00,75.00);

-- Insert Medication Data
insert into MEDICATION (itemID, interactions, dosage)
values('1','NONE', '100 mg/5 mL');

insert into MEDICATION (itemID, interactions, dosage)
values('2','Vaccine A','150 mg/5 mL');

insert into MEDICATION (itemID, interactions, dosage)
values('3','Vaccine D','200 mg/5 mL');

insert into MEDICATION (itemID, interactions, dosage)
values('4','NONE','100 mg/10 mL');

insert into MEDICATION (itemID, interactions, dosage)
values('5','NONE','250 mg/15 mL');

-- Insert Treatment Data
insert into TREATMENT (examID,medID,type,startDate,endDate,directions)
values('1','1','VACCINE','2024-04-15','2024-04-15','N/A One Time Injection');

insert into TREATMENT (examID,medID,type,startDate,endDate,directions)
values('2','2','VACCINE','2024-04-16','2024-04-16','N/A One Time Injection');

insert into TREATMENT (examID,medID,type,startDate,endDate,directions)
values('3','3','VACCINE','2024-03-15','2024-03-15','N/A One Time Injection');

insert into TREATMENT (examID,medID,type,startDate,endDate,directions)
values('4','4','VACCINE','2024-04-10','2024-04-10','N/A One Time Injection');

insert into TREATMENT (examID,medID,type,startDate,endDate,directions)
values('5','5','VACCINE','2024-02-01','2024-02-01','N/A One Time Injection');

insert into TREATMENT (examID,medID,type,startDate,endDate,directions)
values('5',null,'LIFESTYLE','2024-02-01','2024-02-01','Change of Dog Food');

-- Insert Invoice Data

insert into INVOICE (examID,custID,amtDue,status,INVOICEDate)
values('1','1',175.15, 'UNPAID','2024-04-15');

insert into INVOICE (examID,custID,amtDue,status,INVOICEDate)
values('2','2',123.15, 'UNPAID','2024-04-16');

insert into INVOICE (examID,custID,amtDue,status,INVOICEDate)
values('3','3',225.25, 'UNPAID','2024-03-15');

insert into INVOICE (examID,custID,amtDue,status,INVOICEDate)
values('4','4',200.57, 'UNPAID','2024-04-10');

insert into INVOICE (examID,custID,amtDue,status,INVOICEDate)
values('5','5',211.11, 'UNPAID','2024-02-01');

-- Insert Appointment Data
insert into APPOINTMENT (clientID,petID,staffID,start_time,checkin_time,description)
values('1','1','1','2024-04-15 08:30:00','2024-04-15 08:30:00','Yearly Checkup');

insert into APPOINTMENT (clientID,petID,staffID,start_time,checkin_time,description)
values('2','2','1','2024-04-16 08:45:00','2024-04-16 08:45:00','Yearly Checkup');

insert into APPOINTMENT (clientID,petID,staffID,start_time,checkin_time,description)
values('3','3','2','2024-03-15 08:30:00','2024-03-15 08:30:00','Yearly Checkup');

insert into APPOINTMENT (clientID,petID,staffID,start_time,checkin_time,description)
values('4','4','2','2024-04-10 12:30:00','2024-04-10 12:30:00','Yearly Checkup');