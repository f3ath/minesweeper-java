package f3ath.minesweeper.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }
}
