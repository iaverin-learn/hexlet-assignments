package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;


// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping()
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment " + id.toString() + "not found"));
    }
    
    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Comment commentForUpdate = commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
        commentForUpdate.setBody(comment.getBody()); 
        commentForUpdate.setPostId(comment.getPostId());
        commentRepository.save(commentForUpdate);
        return commentForUpdate;
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }


    


    
}
// END
