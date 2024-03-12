import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Planes.dao.UserRepository;
import Planes.dto.User;
import Planes.rest.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAllUsers() {
        List<Planes.vao.User> userList = new ArrayList<>();
        Planes.vao.User user1 = new Planes.vao.User( "John", "Doe", "john@example.com", "password", "USA");
        Planes.vao.User user2 = new Planes.vao.User("Jane", "Doe", "jane@example.com", "password", "UK");
        user1.toDto();
        user2.toDto();
        userList.add(user1);
        userList.add(user2);
        doReturn(userList).when(userRepository).findAll();

        Iterable<User> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(2, ((List<User>) result).size());
    }

    @Test
    public void testPostUser() {
        Planes.vao.User user1 = new Planes.vao.User("John", "Doe", "john@example.com", "password", "USA");
        User userDto = user1.toDto();

        when(userRepository.save(any())).thenReturn(user1);

        ResponseEntity<User> responseEntity = userController.postUser(userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testGetUserById() {
        Planes.vao.User user1 = new Planes.vao.User("John", "Doe", "john@example.com", "password", "USA");
        User userDto = user1.toDto();
        String id = "1";
        user1.setId(id);

        when(userRepository.save(any())).thenReturn(user1);

        userController.postUser(userDto);

        verify(userRepository, times(1)).save(any());

        when(userRepository.findById(id)).thenReturn(Optional.of(user1));

        ResponseEntity<User> responseEntity = userController.getUserById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("John", responseEntity.getBody().name());
    }

    @Test
    public void testDeleteUser() {
        Planes.vao.User user1 = new Planes.vao.User("John", "Doe", "john@example.com", "password", "USA");
        User userDto = user1.toDto();
        String id = "1";
        user1.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user1));

        ResponseEntity<String> responseEntity = userController.deleteUser(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("deleted", responseEntity.getBody());
    }
}
