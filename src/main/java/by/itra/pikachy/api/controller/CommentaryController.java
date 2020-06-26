package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentary")
public class CommentaryController {
    private final CommentaryService commentaryService;

    @Autowired
    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @GetMapping("{postId}")
    public List<Commentary> getCommentariesToPost(@PathVariable int postId) {
        return commentaryService.getCommentariesByPostId(postId);
    }

    @PostMapping
    public CommentaryDto created(@RequestBody CommentaryDto commentaryDto) {
        return commentaryService.created(commentaryDto);
    }

    @DeleteMapping("{id}")
    public void deleteCommentary(@PathVariable int id) {
        commentaryService.deleteCommentary(id);
    }
}