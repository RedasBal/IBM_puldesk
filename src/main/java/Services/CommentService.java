package Services;

import Model.Comment;
import Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TriageService triageService;

    @Autowired
    private TicketService ticketService;

    public Comment save(String text) {
        Comment comment = new Comment();
        comment.setComment(text);
        comment.setDate(LocalDateTime.now());
        comment.setStatus(false);

        Comment saved = commentRepository.save(comment);

        var dto = triageService.analyze(text);
        if (dto != null) {
            ticketService.save(dto, saved.getId());
            saved.setStatus(true);
            commentRepository.save(saved);
        }

        return saved;
    }

    public java.util.List<Comment> getAll() {
        return commentRepository.findAll();
    }
}