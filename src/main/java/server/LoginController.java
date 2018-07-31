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
    public Response signIn(User logInRequest){
        if(userMap.isEmpty()){
            userMap = fileManager.readUsersFromFile();
        }
        if(isUserExists(logInRequest)){
            User user = userMap.get(logInRequest.getUsername());
            if (isCorrectPassword(user, logInRequest.getPassword())){
                if (!user.isOnline()){
                    userMap.get(logInRequest.getUsername()).setOnline(true);
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

    @RequestMapping(value = "/signout", consumes = "application/json", method = RequestMethod.POST)
    public Response signOut(User logOutRequest){
        return new Response("","");
    }

}
