package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/commentary")
public class CommentaryController {
    private final CommentaryService commentaryService;

    @Autowired
    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @GetMapping("{postId}")
    public Page<CommentaryDto> getCommentariesFromPost(@PathVariable @Valid int postId,
                                                       int page, int size, String sort) {
        return commentaryService.getByPostId(postId, page, size, sort);
    }

    @PostMapping
    public CommentaryDto create(@RequestBody @Valid CommentaryDto commentaryDto) {
        return commentaryService.create(commentaryDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @Valid int id) {
        commentaryService.delete(id);
    }

    @MessageMapping("/commentaries")
    @SendTo("/subscribe/commentaries")
    public CommentaryDto addCommentary(CommentaryDto commentary) {
        return commentaryService.create(commentary);
    }
}