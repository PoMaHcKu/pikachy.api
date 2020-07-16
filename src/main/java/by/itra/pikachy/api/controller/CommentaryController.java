package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.service.CommentaryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/commentary")
@AllArgsConstructor
public class CommentaryController {
    private final CommentaryService commentaryService;

    @GetMapping
    public Page<CommentaryDto> getCommentariesFromPost(@RequestParam int postId,
                                                       @RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int size,
                                                       @RequestParam(required = false, defaultValue = "created") String sort) {
        return commentaryService.getByPostId(postId, page, size, sort);
    }

    @PostMapping
    public CommentaryDto create(@RequestBody CommentaryDto commentaryDto, @AuthenticationPrincipal Principal user) {
        return commentaryService.create(commentaryDto, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @Valid int id) {
        commentaryService.delete(id);
    }
}