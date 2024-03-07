package Planes.rest;

import Planes.dao.UserRepository;
import Planes.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class.toString());

    @Autowired
    private UserRepository dao;

    @GetMapping("/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        List<User> ret = new ArrayList<>();
        dao.findAll().forEach(p -> ret.add(p.toDto()));
        return ret;
    }

    @GetMapping("/users/{id}")
    public @ResponseBody ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        Optional<Planes.vao.User> val = dao.findById(id);
        if (val.isEmpty()) {
            log.info(()->"/users/"+id+" ; User not found!");
            return new ResponseEntity("user-not-found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(val.get().toDto());
    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody User user) {
        Planes.vao.User vao = dao.save(new Planes.vao.User(user));
        return ResponseEntity.ok(vao.toDto());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> putUser(@PathVariable("id") int id, @RequestBody User user) {
        Optional<Planes.vao.User> val = dao.findById(id);
        if (!val.isPresent()) {
            log.info("/users/"+id+" ; User not found!");
            return ResponseEntity.notFound().build();
        }

        Planes.vao.User vao = val.get();
        vao.updateFrom(user);
        vao = dao.save(vao);
        return ResponseEntity.ok(vao.toDto());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        Optional<Planes.vao.User> val = dao.findById(id);
        if (val.isEmpty()) {
            log.info("/users/"+id+" ; User not found!");
            return new ResponseEntity("user-not-found",HttpStatus.NOT_ACCEPTABLE);
        }
        Planes.vao.User vao = val.get();
        dao.delete(vao);
        return ResponseEntity.ok("deleted");
    }

}