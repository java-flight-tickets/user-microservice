package Planes.vao;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Data @NoArgsConstructor
@Table(name = "users")
public class User {

    public User(Planes.dto.User dto) {
        setName(dto.name());
        setSurname(dto.surname());
        setEmail(dto.email());
        setPassword(dto.password());
        setCountry(dto.country());
    }

    public void updateFrom(Planes.dto.User dto) {
        setName(dto.name());
        setSurname(dto.surname());
        setEmail(dto.email());
        setPassword(dto.password());
        setCountry(dto.country());
    }

    public Planes.dto.User toDto() {
        return new Planes.dto.User(
                getId(),
                getName(),
                getSurname(),
                getEmail(),
                getPassword(),
                getCountry());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected String country;

}