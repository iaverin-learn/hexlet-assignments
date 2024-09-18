package exercise;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import lombok.Value;
import exercise.component.UserProperties;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();

    @Autowired // Аннотация нужна для автоподстановки объекта
    private UserProperties usersAdmins;

    // BEGIN
    @GetMapping("/admins")
    public List<String> getAdmins() {
        List<String> adminEmails = usersAdmins.getAdmins();
        List<String> adminNames = users
                .stream()
                .filter(p -> adminEmails.contains(p.getEmail()))
                .sorted(Comparator.comparing(User::getName))
                .map(User::getName)
                .collect(Collectors.toList());
        return adminNames;
    }

    // END
    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
