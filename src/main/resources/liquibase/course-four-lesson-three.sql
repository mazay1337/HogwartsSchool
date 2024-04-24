--changeset formatted sql

--changeset mazay:course-four-lesson-three.1
CREATE  INDEX IDX_STUDENTS_NAME ON students(name);

--changeset mazay:course-four-lesson-three.2
CREATE  INDEX IDX_FACULTIES_NAME_COLOR ON faculties(name, color);