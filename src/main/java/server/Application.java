package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final String REGISTERED_USERs_FILENAME = "REGISTERED_USERS_FILENAME.txt";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
