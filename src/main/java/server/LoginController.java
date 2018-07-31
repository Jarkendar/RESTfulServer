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
    public Response signIn(User loginJSON){
        if(userMap.isEmpty()){
            userMap = fileManager.readUsersFromFile();
        }
        if(userMap.get(loginJSON.getUsername()))
        return ;
    }



}
