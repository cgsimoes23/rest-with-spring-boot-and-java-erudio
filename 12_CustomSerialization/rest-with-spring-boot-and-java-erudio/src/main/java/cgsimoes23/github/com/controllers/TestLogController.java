package cgsimoes23.github.com.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/api/test/v1")
    public String testLog() {
        logger.debug("This is a DEBUG log!");
        logger.info("This is a INFO log!");
        logger.warn("This is a WARN log!");
        logger.error("This is a ERROR log!");
        return "Logs generated successfully!";
    }

}
