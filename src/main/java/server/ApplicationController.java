package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import server.models.Challenge;
import server.models.Response;
import server.models.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(produces = "application/json")
public class ApplicationController {

    private Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    private Map<String, User> userMap = new HashMap<>();
    private FileManager fileManager = new FileManager(new File(Application.REGISTERED_USERS_FILENAME));

    public Map<String, User> getUserMap() {
        return userMap;
    }

    @PostMapping(value = "/login/signin", consumes = "application/json")
    public Response signIn(@RequestBody User logInRequest) {
        logger.info("sign in user = {}", logInRequest);
        prepareUserMap();
        if (isUserExists(logInRequest)) {
            User user = userMap.get(logInRequest.getUsername());
            if (isCorrectPassword(user, logInRequest.getPassword())) {
                if (!user.isOnline()) {
                    userMap.get(logInRequest.getUsername()).setOnline(true);
                    userMap.get(logInRequest.getUsername()).setToken(generateToken(logInRequest.getUsername()));
                    logger.info("sign in success = {}", userMap.get(logInRequest.getUsername()));
                    return new Response(Statuses.OK.toString(), "Successful sign in. You are online now.", userMap.get(logInRequest.getUsername()).getToken());
                } else {
                    logger.info("sign in - User is online = {}", logInRequest);
                    return new Response(Statuses.LOGGED.toString(), "User is online.", userMap.get(logInRequest.getUsername()).getToken());
                }
            } else {
                logger.info("sign in - Wrong password = {}", logInRequest);
                return new Response(Statuses.INCORRECT_PASSWORD.toString(), "Wrong password.");
            }
        } else {
            logger.info("sign in - User not exist = {}", logInRequest);
            return new Response(Statuses.NOT_EXISTS.toString(), "User not exists.");
        }
    }

    @PutMapping(value = "/login/signout", consumes = "application/json")
    public Response signOut(@RequestBody User logOutRequest) {
        logger.info("sign out user = {}", logOutRequest);
        prepareUserMap();
        if (isUserExists(logOutRequest)) {
            User user = userMap.get(logOutRequest.getUsername());
            if (user.isOnline()) {
                userMap.get(logOutRequest.getUsername()).setOnline(false);
                logger.info("sign out success = {}", userMap.get(logOutRequest.getUsername()));
                return new Response(Statuses.OK.toString(), "User is offline");
            } else {
                logger.info("sign out - User is not logged = {}", logOutRequest);
                return new Response(Statuses.NOT_LOGGED.toString(), "User is not logged.");
            }
        } else {
            logger.info("sign out - User not exists = {}", logOutRequest);
            return new Response(Statuses.NOT_EXISTS.toString(), "User not exists.");
        }
    }

    @PostMapping(value = "/login/register", consumes = "application/json")
    public Response register(@RequestBody User newUser) {
        logger.info("register user = {}", newUser);
        prepareUserMap();
        if (!isUserExists(newUser)) {
            userMap.put(newUser.getUsername(), new User(fileManager.getNextID(), newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), false));
            fileManager.saveUsersToFile(userMap);
            logger.info("register success = {}", userMap.get(newUser.getUsername()));
            return new Response(Statuses.OK.toString(), "New user register.");
        } else {
            logger.info("register - User already exists = {}", newUser);
            return new Response(Statuses.EXISTS.toString(), "User already exists.");
        }
    }

    @PostMapping(value = "/challenge/insert", consumes = "application/json")
    public Response insertChallenge(Challenge challenge) {
        if (userMap.containsKey(challenge.getUserReceiver())) {
            fileManager.saveChallenge(challenge);
            return new Response(Statuses.CHALLENGE_SAVED.toString(), "Challenge saved");
        } else {
            return new Response(Statuses.USER_NOT_LOGGED.toString(), "User is not logged");
        }
    }

    private void prepareUserMap() {
        if (userMap.isEmpty()) {
            userMap = fileManager.readUsersFromFile();
        }
    }

    private boolean isUserExists(User request) {
        return userMap.get(request.getUsername()) != null;
    }

    private boolean isCorrectPassword(User user, String potentialPassword) {
        return user.getPassword().equals(potentialPassword);
    }

    private String generateToken(String userName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : userName.toCharArray()) {
            stringBuilder.append(Integer.toHexString(c));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
        stringBuilder.append(simpleDateFormat.format(new Date()));
        return stringBuilder.toString();
    }


}
