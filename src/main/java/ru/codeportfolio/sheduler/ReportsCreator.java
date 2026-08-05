package ru.codeportfolio.sheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ReportsCreator {


    @Scheduled(cron = "0 0 0 * * *")
    public void execute() {
        // ...
    }
}
