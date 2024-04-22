alter table student add CONSTRAINT age_check CHECK ( age >= 16 );
alter table student add CONSTRAINT name_unique UNIQUE (name);
alter table student alter COLUMN name SET NOT NULL;
alter table student alter COLUMN age SET DEFAULT 20;
alter table faculties add CONSTRAINT name_color_unique UNIQUE (name, color);