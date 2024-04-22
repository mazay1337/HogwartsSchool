--у каждого человека есть машина. Причем несколько человек могут пользоваться одной машиной.
--У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
--У каждой машины есть марка, модель и стоимость.
--Также не забудьте добавить таблицам первичные ключи и связать их.

CREATE TABLE cars(
    id    BIGSERIAL primary key,
    brand VARCHAR(32) NOT NULL,
    model VARCHAR(32) NOT NULL,
    price INT NOT NULL CHECK (price > 0)
);

CREATE TABLE drivers(
    id    BIGSERIAL primary key,
    name  VARCHAR(32) NOT NULL,
    age   INT NOT NULL CHECK(age > 0),
    has_driver_licanse BOOL DEFAULT false,
    car_id BEGINT REFERENCES cars(id)
);

INSERT INTO cars(brand, model, price) VALUES('Kia', 'Rio', 2000000);
INSERT INTO drivers(name, age) VALUES ('Ivan Ovanov', 22);

select * from cars;
update drivers set car_id = 1;