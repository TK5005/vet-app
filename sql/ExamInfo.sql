use vetapp;
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