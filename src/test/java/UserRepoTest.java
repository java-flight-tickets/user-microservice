import static org.junit.jupiter.api.Assertions.assertEquals;

import Planes.SpringPlanesApplication;
import Planes.dao.UserRepository;
import Planes.vao.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest(classes = SpringPlanesApplication.class)
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        user = userRepository.save(new User(
                new Planes.dto.User(
                        0, "Test", "User", "test@example.com", "password123", "Slovenia"
                )
        ));
    }

    @Test
    void addUserTest() {
        assertEquals(1, userRepository.count());
        assertEquals("Test", user.getName());
        assertEquals("User", user.getSurname());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("Slovenia", user.getCountry());
    }

    @Test
    void deleteUserTest() {
        assertEquals(1, userRepository.count());
        userRepository.delete(user);
        assertEquals(0, userRepository.count());
    }

}
