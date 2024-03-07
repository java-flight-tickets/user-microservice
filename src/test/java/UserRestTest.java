import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Planes.SpringPlanesApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import Planes.dao.UserRepository;
import Planes.dto.User;
import Planes.rest.UserController;

@SpringBootTest(classes = SpringPlanesApplication.class)
public class UserRestTest {

    @Autowired
    UserController userRest;

    User newUser = null;

    @BeforeEach
    void createUser() {
        newUser = userRest.postUser(
                new User(0,"User","Test","email", "geslo", "Slo")
        ).getBody();
    }

    @Test
    void checkProductExistence() {
        User fromServer = userRest.getUserById(newUser.id()).getBody();
        assertEquals(fromServer.name(),newUser.name());
        assertEquals(fromServer.surname(),newUser.surname());
        assertEquals(fromServer.email(),newUser.email());
        assertEquals(fromServer.password(),newUser.password());
    }

    @Test
    void postUserTest() {
        ResponseEntity<User> response = userRest.postUser(newUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        User createdUser = response.getBody();
        assertEquals(newUser.name(), createdUser.name());
        assertEquals(newUser.surname(), createdUser.surname());
        assertEquals(newUser.email(), createdUser.email());
        assertEquals(newUser.password(), createdUser.password());
        assertEquals(newUser.country(), createdUser.country());
    }

    @Test
    void deleteUserTest() {
        int countBeforeDelete = countUsers();

        ResponseEntity<String> response = userRest.deleteUser(newUser.id());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted", response.getBody());

        int countAfterDelete = countUsers();
        assertEquals(countBeforeDelete - 1, countAfterDelete);
    }

    private int countUsers() {
        int count = 0;
        Iterable<User> users = userRest.getAllUsers();
        for (User user : users) {
            count++;
        }
        return count;
    }

    @Test
    void updateUserTest() {
        User updatedUser = new User(newUser.id(), "Updated", "User", "updatedemail", "updatedpassword", "UpdatedCountry");
        ResponseEntity<User> response = userRest.putUser(newUser.id(), updatedUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User fromServer = userRest.getUserById(newUser.id()).getBody();
        assertEquals(updatedUser.name(), fromServer.name());
        assertEquals(updatedUser.surname(), fromServer.surname());
        assertEquals(updatedUser.email(), fromServer.email());
        assertEquals(updatedUser.password(), fromServer.password());
        assertEquals(updatedUser.country(), fromServer.country());
    }
}
