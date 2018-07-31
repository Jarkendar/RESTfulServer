package server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.models.User;
import server.models.Response;

import java.io.File;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    private HashMap<String, User> userMap = new HashMap<>();
    private FileManager fileManager = new FileManager(new File(Application.REGISTERED_USERs_FILENAME));

    @RequestMapping(value = "/signin", consumes = "application/json", method = RequestMethod.POST)
    public Response signIn(User loginRequest){
        if(userMap.isEmpty()){
            userMap = fileManager.readUsersFromFile();
        }
        if(isUserExists(loginRequest)){
            User user = userMap.get(loginRequest.getUsername());
            if (isCorrectPassword(user, loginRequest.getPassword())){
                if (!user.isOnline()){
                    userMap.get(loginRequest.getUsername()).setOnline(true);
                    return new Response(Statuses.OK.toString(), "Successful sign in. You are online now.");
                }else {
                    return new Response(Statuses.LOGGED.toString(), "User is online.");
                }
            } else {
                return new Response(Statuses.INCORRECT_PASSWORD.toString(), "Wrong password.");
            }
        }else {
            return new Response(Statuses.NOT_EXISTS.toString(),"User not exists.");
        }
    }

    private boolean isUserExists(User request){
        return userMap.get(request.getUsername()) != null;
    }

    private boolean isCorrectPassword(User user, String potentialPassword){
        return user.getPassword().equals(potentialPassword);
    }



}
