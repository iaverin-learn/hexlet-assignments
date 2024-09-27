package exercise.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import exercise.components.DaytimeBean;


// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    private DaytimeBean daytimeBean;
    
    @GetMapping("/welcome")
    public String getDayTime(){
        return daytimeBean.getName();
    }
}
// END
