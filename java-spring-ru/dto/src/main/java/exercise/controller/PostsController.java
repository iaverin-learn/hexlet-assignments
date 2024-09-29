package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.model.Comment;

import exercise.repository.PostRepository;
import jakarta.websocket.server.PathParam;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;
import org.springframework.web.bind.annotation.RequestParam;


// BEGIN

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> getPosts() {
        var posts = postRepository.findAll();
        
        return posts.stream().map(this::fetchCommentsAndCreatePostDTO).toList();

    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + Long.toString(id) + " not found"));
        return fetchCommentsAndCreatePostDTO(post);
    }

    private PostDTO fetchCommentsAndCreatePostDTO(Post post) {
        var dto = new PostDTO();
        dto.setBody(post.getBody());
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        
        var comments = commentRepository.findByPostId(post.getId());
        List<CommentDTO> commentsDTO= comments.stream().map(this::toCommentDTO).toList();
        dto.setComments(commentsDTO);
        return dto;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }
} 

// END
