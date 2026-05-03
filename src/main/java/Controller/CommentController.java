package Controller;

import Model.Comment;
import Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment post(@RequestBody String text) {
        return commentService.save(text);
    }

    @GetMapping
    public List<Comment> getAll() {
        return commentService.getAll();
    }
}