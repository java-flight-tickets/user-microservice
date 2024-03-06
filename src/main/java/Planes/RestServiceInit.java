package Planes;

import Planes.vao.User;
import Planes.dao.UserRepository;

import java.util.logging.Logger;

public class RestServiceInit {

    private static final Logger log = Logger.getLogger(RestServiceInit.class.toString());

    void populateTestDataIfNotPresent(
            UserRepository daoUser
    ) {
        if (daoUser.count() > 0) {
            log.info("populateTestData - skipped.");
            return;
        }
        log.info("populateTestData initiated.");

        User p1 = new User();
        p1.setName("Lana");
        p1.setSurname("Benedicic");
        p1.setEmail("lana.ben@gmail.com");
        p1.setPassword("geslo123");
        p1.setCountry("Slovenia");
    }

}