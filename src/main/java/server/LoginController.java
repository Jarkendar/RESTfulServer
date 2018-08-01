package server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.models.User;
import server.models.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    private HashMap<String, User> userMap = new HashMap<>();
    private FileManager fileManager = new FileManager(new File(Application.REGISTERED_USERs_FILENAME));

    @RequestMapping(value = "/signin", consumes = "application/json", method = RequestMethod.POST)
    public Response signIn(User logInRequest){
        prepareUserMap();
        if(isUserExists(logInRequest)){
            User user = userMap.get(logInRequest.getUsername());
            if (isCorrectPassword(user, logInRequest.getPassword())){
                if (!user.isOnline()){
                    userMap.get(logInRequest.getUsername()).setOnline(true);
                    userMap.get(logInRequest.getUsername()).setTOKEN(generateToken(logInRequest.getUsername()));
                    return new Response(Statuses.OK.toString(), "Successful sign in. You are online now.", userMap.get(logInRequest.getUsername()).getTOKEN());
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

    @RequestMapping(value = "/signout", consumes = "application/json", method = RequestMethod.PUT)
    public Response signOut(User logOutRequest){
        prepareUserMap();
        if (isUserExists(logOutRequest)){
            User user = userMap.get(logOutRequest.getUsername());
            if (user.isOnline()){
                userMap.get(logOutRequest.getUsername()).setOnline(false);
                return new Response(Statuses.OK.toString(), "User is offline");
            }else{
                return new Response(Statuses.NOT_LOGGED.toString(), "User is not logged.");
            }
        }else {
            return new Response(Statuses.NOT_EXISTS.toString(), "User not exists.");
        }
    }

    @RequestMapping(value = "/register", consumes = "application/json", method = RequestMethod.POST)
    public Response register(User newUser){
        prepareUserMap();
        if (!isUserExists(newUser)){
            userMap.put(newUser.getUsername(), new User(fileManager.getNextID(), newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), false));
            fileManager.saveUsersToFile(userMap);
            return new Response(Statuses.OK.toString(), "New user register.");
        }else {
            return new Response(Statuses.EXISTS.toString(),"User already exists.");
        }
    }

    private void prepareUserMap(){
        if (userMap.isEmpty()){
            userMap = fileManager.readUsersFromFile();
        }
    }

    private boolean isUserExists(User request){
        return userMap.get(request.getUsername()) != null;
    }

    private boolean isCorrectPassword(User user, String potentialPassword){
        return user.getPassword().equals(potentialPassword);
    }

    private String generateToken(String userName){
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : userName.toCharArray()){
            stringBuilder.append(Integer.toHexString(c));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
        stringBuilder.append(simpleDateFormat.format(new Date()));
        return stringBuilder.toString();
    }



}
