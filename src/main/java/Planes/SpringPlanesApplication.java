package Planes;

import Planes.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan("Planes")
public class SpringPlanesApplication {

	private static final Logger log = Logger.getLogger(SpringPlanesApplication.class.toString());

	public static void main(String[] args) {
		SpringApplication.run(SpringPlanesApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository daoUser) {
		return args -> {
			log.info("Running..");
			new RestServiceInit().populateTestDataIfNotPresent(daoUser);
		};
	}

}
