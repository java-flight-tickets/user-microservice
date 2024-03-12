package Planes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public record User (
        int id,
        String name,
        String surname,
        String email,
        String password,
        String country) {

    public User(String name, String surname) {
        this(0, name, surname, null, null, null);
    }

    public User(String name, String surname, String email, String password, String country) {
        this(0, name, surname, email, password, country);
    }
}
