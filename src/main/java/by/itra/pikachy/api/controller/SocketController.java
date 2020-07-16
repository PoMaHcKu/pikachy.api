package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.service.CommentaryService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class SocketController {
    private final CommentaryService commentaryService;

    @MessageMapping("/commentary/{postId}")
    public CommentaryDto create(@Payload CommentaryDto commentary,
                                @AuthenticationPrincipal Principal user) {
        return user == null ? null : commentaryService.create(commentary, user);
    }
}
