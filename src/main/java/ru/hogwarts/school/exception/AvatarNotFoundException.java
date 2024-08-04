package ru.hogwarts.school.exception;

public class AvatarNotFoundException extends NotFoundException{

    private final long studentId;

    public AvatarNotFoundException(long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String getMessage() {
        return "Avatar for student with id: %d not found!".formatted(studentId);
    }
}
