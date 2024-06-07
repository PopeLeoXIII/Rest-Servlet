package org.example.servlet.dto.user;


public class UserUpdateDto {
    private Long id;
    private String name;
    private String surname;

    public UserUpdateDto() {}

    public UserUpdateDto(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
