package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/greet", produces = "application/json")
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        logger.info("GET method = message to posterity");
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value = "/great", method = RequestMethod.POST)
    public ResponseEntity<Greeting> postGreat(@RequestBody Greeting greeting){
        logger.info("POST method = message to posterity");
        Greeting response = new Greeting(greeting.getId()+1, greeting.getContent()+" POST method response");
        return new ResponseEntity<Greeting>(response, HttpStatus.OK);
    }


}