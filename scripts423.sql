SELECT s.name, s.age, f.name FROM students s LEFT JOIN faculties f ON s.faculty_id = f.id;
SELECT s.name FROM students s INNER JOIN avatars a ON a.student_id = s.id;