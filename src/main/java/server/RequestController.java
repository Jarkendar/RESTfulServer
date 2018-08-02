package server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.models.PlainText;

import java.util.Date;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class RequestController {

    private Logger logger = LoggerFactory.getLogger(RequestController.class);

    @GetMapping(value = "/")
    public String defaultRequestPing() {
        logger.info("GET default request");
        return "Server status = work, locale time = " + new Date().toString();
    }


    @GetMapping(value = "/ping")
    public PlainText getPing() {
        return new PlainText(new Date().toString());
    }


}
