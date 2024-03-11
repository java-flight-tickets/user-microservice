import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import Planes.SpringPlanesApplication;
import Planes.dto.User;
import Planes.rest.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = SpringPlanesApplication.class)
public class UserRestTest {

    @Autowired
    UserController userRest;

    User newUser = null;

    @BeforeEach
    void createUser() {
        newUser = userRest.postUser(
                new User(0, "User", "Test", "email", "geslo", "Slo")
        ).getBody();
    }

    @AfterEach
    void deleteUser() {
        ResponseEntity<String> response = userRest.deleteUser(0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted", response.getBody());
    }

    @Test
    void checkProductExistence() {
        ResponseEntity<User> responseEntity = userRest.getUserById(newUser.id());
        assertNotNull(responseEntity.getBody());
        User fromServer = responseEntity.getBody();
        assertEquals(newUser.name(), fromServer.name());
        assertEquals(newUser.surname(), fromServer.surname());
        assertEquals(newUser.email(), fromServer.email());
        assertEquals(newUser.password(), fromServer.password());
        assertEquals(newUser.country(), fromServer.country());
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
