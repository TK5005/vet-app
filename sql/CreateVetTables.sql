use vetapp;
CREATE TABLE APPOINTMENT(
        apptNo INT NOT NULL AUTO_INCREMENT,
        clientID INT NOT NULL,
        petID INT NOT NULL,
        staffID INT,
        date_time DATETIME NOT NULL,
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
        medID INT NOT NULL,
        interactions VARCHAR(250),
        primary key (medID));

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



ALTER TABLE APPOINTMENT ADD CONSTRAINT FOREIGN KEY (clientID) REFERENCES CLIENT (clientID);
ALTER TABLE APPOINTMENT ADD CONSTRAINT FOREIGN KEY (petID) REFERENCES PET (petID);
ALTER TABLE APPOINTMENT ADD CONSTRAINT FOREIGN KEY (staffID) REFERENCES STAFF (empID);

ALTER TABLE MEDICATION ADD CONSTRAINT FOREIGN KEY (medID) REFERENCES INVENTORY (itemID);

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
ALTER TABLE PRESCRIPTION ADD CONSTRAINT FOREIGN KEY (medID) REFERENCES MEDICATION (medID);
ALTER TABLE PRESCRIPTION ADD CONSTRAINT FOREIGN KEY (trmntID) REFERENCES TREATMENT (trmntID);
ALTER TABLE TREATMENT ADD CONSTRAINT FOREIGN KEY (examID) REFERENCES EXAMINATION (examID);
ALTER TABLE TREATMENT ADD CONSTRAINT FOREIGN KEY (medID) REFERENCES MEDICATION(medID);
ALTER TABLE INVOICE ADD CONSTRAINT FOREIGN KEY (examID) REFERENCES EXAMINATION (examID);
ALTER TABLE INVOICE ADD CONSTRAINT FOREIGN KEY (custID) REFERENCES CLIENT (clientID);
