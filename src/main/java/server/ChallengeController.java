package server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.models.Response;

@RestController
@RequestMapping(value = "/challenge", produces = "application/json")
public class ChallengeController {

    @PostMapping(value = "/insert", consumes = "application/json")
    public Response insertChallenge(){return null;}
}
