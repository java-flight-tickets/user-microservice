package Planes.rest;

import Planes.dao.UserRepository;
import Planes.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

}