use vetapp;
insert into STAFF (firstName,lastName,sex,dob,ssn,phone,street,city,state,zip)
values('Jenny','McCann','F','1988-07-01','123456789','555-999-1212','321 Shamrock Rd','Bel Air', 'MD', 21014);

insert into VET(empID,licenseNo)
values('1','VC193990230909');


insert into STAFF (firstName,lastName,sex,dob,ssn,phone,street,city,state,zip)
values('Danielle','Cherry','F','1990-10-01','666554444','555-999-1234','876 Cherry Rd','Howell', 'NJ', 085223);

insert into TECH(empID,certNumber)
values('2','VT123403782783592');

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