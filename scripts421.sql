alter table student add CONSTRAINT age_check CHECK ( age >= 16 );
alter table student add CONSTRAINT name_unique UNIQUE (name);
alter table student alter COLUMN name SET NOT NULL;
alter table student alter COLUMN age SET DEFAULT 20;
alter table faculties add CONSTRAINT name_color_unique UNIQUE (name, color);



create table person
(
    id        integer primary key,
    name      text,
    age       integer,
    is_driver boolean,
    car_id    integer not null references car (id)
);



create table car
(
    id    integer primary key,
    make  text,
    model text,
    price integer
);


select student.name, student.age
from student
         inner join faculty on student.faculty_id = faculty.id

select student.name, student.age
from student
         inner join avatar on student.id = avatar.id