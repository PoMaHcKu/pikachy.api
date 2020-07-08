package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SocketController {

    private final CommentaryService commentaryService;

    @Autowired
    public SocketController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @MessageMapping("/commentary")
    @SendTo("/chat/commentaries")
    public CommentaryDto create(@Payload CommentaryDto commentary, @AuthenticationPrincipal Principal user) {
        return user == null ? null : commentaryService.create(commentary, user);
    }
}
