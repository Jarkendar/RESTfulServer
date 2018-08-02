package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is my first RESTful server. It isn't security. It isn't commercial. It created to learn new things.
 */

@SpringBootApplication
public class Application {

    public static final String REGISTERED_USERS_FILENAME = "REGISTERED_USERS_FILENAME.txt";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
