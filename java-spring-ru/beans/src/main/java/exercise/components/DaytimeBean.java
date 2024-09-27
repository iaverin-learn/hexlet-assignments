package exercise.components;
import org.springframework.stereotype.Component;
import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.LocalDateTime;

@Component
public class DaytimeBean implements Daytime {
    public String getName() {
        int currentTime = LocalDateTime.now().getHour();
        if (currentTime > 6 && currentTime <= 22) {
            return new Day().getName();
        }  
        return new Night().getName();
    }

    @PostConstruct
    public void init() {
        System.out.println("Bean is initialized!");
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("Bean is about to destroy");
    }

}
