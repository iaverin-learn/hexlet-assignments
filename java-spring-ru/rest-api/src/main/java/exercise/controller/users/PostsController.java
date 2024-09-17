package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import exercise.model.Post;
import exercise.model.PostCreateDto;
import jakarta.websocket.server.PathParam;
import exercise.Data;

// BEGIN
@RestController
public class PostsController {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    @PostMapping("/api/users/{userId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable() int userId, @RequestBody Post post) {
        post.setUserId(userId);
        posts.add(post);
        return post;
    }

    @GetMapping("api/users/{userId}/posts")
    public List<Post> index(@PathVariable() int userId) {
        List<Post> userPosts = posts.stream()
                .filter(p -> p.getUserId() == userId)
                .collect(Collectors.toList());
        return userPosts;
    }

}

// END
