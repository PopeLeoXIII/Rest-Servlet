package org.example.servlet.dto.user;

public class UserIncomingDto {
    private String name;
    private String surname;

    public UserIncomingDto() {}

    public UserIncomingDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
