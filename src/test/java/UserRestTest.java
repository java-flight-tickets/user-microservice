import static org.junit.jupiter.api.Assertions.assertEquals;

import Planes.SpringPlanesApplication;
import Planes.dto.User;
import Planes.rest.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import Planes.dao.UserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringPlanesApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserRestTest {

    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        Planes.vao.User user1 = new Planes.vao.User("Janez1", "Novak1", "janez1@example.com", "password123", "Slovenia");

        userRepository.save(user1);

        List<User> fetchedUsers = (List<User>) controller.getAllUsers();

        assertEquals(1, fetchedUsers.size());

        controller.deleteUser(user1.getId());
        fetchedUsers = (List<User>) controller.getAllUsers();

        assertEquals(0, fetchedUsers.size());
    }

    @Test
    public void testGetUserById() {
        Planes.vao.User user1 = new Planes.vao.User("Janez1", "Novak1", "janez1@example.com", "password123", "Slovenia");
        userRepository.save(user1);

        User fetchedUser = controller.getUserById(user1.getId()).getBody();

        assertEquals(user1.getId(), fetchedUser.id());
        assertEquals(user1.getName(), fetchedUser.name());
        assertEquals(user1.getSurname(), fetchedUser.surname());
        assertEquals(user1.getEmail(), fetchedUser.email());
        assertEquals(user1.getPassword(), fetchedUser.password());
        assertEquals(user1.getCountry(), fetchedUser.country());

        controller.deleteUser(user1.getId());
        List<User> fetchedUsers = (List<User>) controller.getAllUsers();
        fetchedUsers = (List<User>) controller.getAllUsers();

        assertEquals(0, fetchedUsers.size());
    }

    @Test
    public void testPostUser() {
        Planes.vao.User user1 = new Planes.vao.User("Janez1", "Novak1", "janez1@example.com", "password123", "Slovenia");
        User user = user1.toDto();
        controller.postUser(user);

        User fetchedUser = controller.getUserById(user1.getId()).getBody();
        assertEquals(user1.getId(), fetchedUser.id());
        assertEquals(user1.getName(), fetchedUser.name());
        assertEquals(user1.getSurname(), fetchedUser.surname());
        assertEquals(user1.getEmail(), fetchedUser.email());
        assertEquals(user1.getPassword(), fetchedUser.password());
        assertEquals(user1.getCountry(), fetchedUser.country());

        controller.deleteUser(user1.getId());
    }

    @Test
    public void testDeleteUser() {
        Planes.vao.User user1 = new Planes.vao.User("Janez1", "Novak1", "janez1@example.com", "password123", "Slovenia");
        User user = user1.toDto();
        controller.postUser(user);

        User fetchedUser = controller.getUserById(user1.getId()).getBody();
        assertEquals(user1.getId(), fetchedUser.id());
        assertEquals(user1.getName(), fetchedUser.name());
        assertEquals(user1.getSurname(), fetchedUser.surname());
        assertEquals(user1.getEmail(), fetchedUser.email());
        assertEquals(user1.getPassword(), fetchedUser.password());
        assertEquals(user1.getCountry(), fetchedUser.country());

        controller.deleteUser(user1.getId());
        List<User> fetchedUsers = (List<User>) controller.getAllUsers();

        assertEquals(0, fetchedUsers.size());
    }

}
